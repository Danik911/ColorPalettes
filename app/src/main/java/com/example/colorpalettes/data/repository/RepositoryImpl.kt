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

}