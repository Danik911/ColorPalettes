package com.example.colorpalettes.data.repository

import com.backendless.rt.data.RelationStatus
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.repository.BackendlessDataSource
import com.example.colorpalettes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val backendlessDataSource: BackendlessDataSource
) : Repository {
    override suspend fun getColorPalettes(): List<ColorPalette> {
        return backendlessDataSource.getColorPalettes()
    }

    override suspend fun getLikeCount(objectId: String): ColorPalette {
        return backendlessDataSource.getLikeCount(objectId = objectId)
    }

    override suspend fun observeAddRelation(): Flow<RelationStatus?> {
        return backendlessDataSource.observeAddRelation()
    }

    override suspend fun observeDeleteRelation(): Flow<RelationStatus?> {
        return backendlessDataSource.observeDeleteRelation()
    }

    override suspend fun observeApproval(): Flow<ColorPalette> {
        return backendlessDataSource.observeApproval()
    }

    override suspend fun observeDeletedPalettes(): Flow<ColorPalette> {
        return backendlessDataSource.observeDeletedPalettes()
    }

    override suspend fun checkSavedPalette(
        paletteObjectId: String,
        userObjectId: String
    ): List<ColorPalette> {
        return backendlessDataSource.checkSavedPalette(
            paletteObjectId = paletteObjectId,
            userObjectId = userObjectId
        )
    }

    override suspend fun saveColorPalette(paletteObjectId: String, userObjectId: String): Int {
        return backendlessDataSource.saveColorPalette(
            paletteObjectId = paletteObjectId,
            userObjectId = userObjectId
        )
    }

    override suspend fun deleteColorPalette(paletteObjectId: String, userObjectId: String): Int {
        return backendlessDataSource.deleteColorPalette(
            paletteObjectId = paletteObjectId,
            userObjectId = userObjectId
        )
    }

    override suspend fun addLike(paletteObjectId: String, userObjectId: String): Int? {
        return backendlessDataSource.addLike(
            paletteObjectId = paletteObjectId,
            userObjectId = userObjectId
        )
    }

    override suspend fun removeLike(paletteObjectId: String, userObjectId: String): Int? {
        return backendlessDataSource.removeLike(
            paletteObjectId = paletteObjectId,
            userObjectId = userObjectId
        )
    }

}