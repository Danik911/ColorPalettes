package com.example.colorpalettes.data.repository

import com.backendless.Persistence
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.repository.BackendlessDataSource
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BackendlessDataSourceImpl @Inject constructor(
    private val backendless: Persistence
) : BackendlessDataSource {
    override suspend fun getColorPalettes(): List<ColorPalette> {
        val queryBuilder: DataQueryBuilder = DataQueryBuilder
            .create()
            .setProperties("Count(likes) as totalLikes", "colors", "approved", "objectId")
            .setWhereClause("approved = true")
            .setGroupBy("objectId")
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

}