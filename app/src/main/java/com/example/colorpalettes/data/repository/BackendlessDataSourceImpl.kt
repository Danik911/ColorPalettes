package com.example.colorpalettes.data.repository

import com.backendless.Persistence
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.backendless.rt.data.RelationStatus
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.repository.BackendlessDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class BackendlessDataSourceImpl @Inject constructor(
    private val backendless: Persistence
) : BackendlessDataSource {
    override suspend fun getColorPalettes(): List<ColorPalette> {
        val queryBuilder: DataQueryBuilder = DataQueryBuilder.create()
            .setProperties("Count(likes) as totalLikes", "colors", "approved", "objectId")
            .setWhereClause("approved = true").setGroupBy("objectId")
        return suspendCoroutine { continuation ->
            backendless.of(ColorPalette::class.java)
                .find(queryBuilder, object : AsyncCallback<List<ColorPalette>> {
                    override fun handleResponse(response: List<ColorPalette>) {
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        continuation.resume(emptyList())
                    }
                })

        }
    }

    override suspend fun getLikeCount(objectId: String): ColorPalette {
        val queryBuilder: DataQueryBuilder =
            DataQueryBuilder.create().setProperties("Count(likes) as totalLikes")

        return suspendCoroutine { continuation ->
            backendless.of(ColorPalette::class.java)
                .findById(objectId, queryBuilder, object : AsyncCallback<ColorPalette> {
                    override fun handleResponse(response: ColorPalette) {
                        continuation.resume(response)
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        continuation.resumeWithException(Exception(fault.message))
                    }

                })
        }
    }

    override suspend fun observeAddRelation(): Flow<RelationStatus?> {
        return callbackFlow {
            val event = backendless.of(ColorPalette::class.java).rt()
            val callback = object : AsyncCallback<RelationStatus> {
                override fun handleResponse(response: RelationStatus?) {
                    trySendBlocking(response)
                }

                override fun handleFault(fault: BackendlessFault?) {
                    fault?.message?.let { cancel(message = it) }
                }
            }
            event.addAddRelationListener("likes", callback)
            awaitClose {
                event.removeAddRelationListeners()
            }
        }
    }

    override suspend fun observeDeleteRelation(): Flow<RelationStatus?> {
        return callbackFlow {
            val event = backendless.of(ColorPalette::class.java).rt()
            val callback = object : AsyncCallback<RelationStatus> {
                override fun handleResponse(response: RelationStatus?) {
                    trySendBlocking(response)
                }

                override fun handleFault(fault: BackendlessFault?) {
                    fault?.message?.let { cancel(message = it) }
                }
            }
            event.addDeleteRelationListener("likes", callback)
            awaitClose {
                event.removeDeleteRelationListeners()
            }
        }
    }

}