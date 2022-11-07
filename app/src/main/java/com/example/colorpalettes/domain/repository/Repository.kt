package com.example.colorpalettes.domain.repository

import com.backendless.rt.data.RelationStatus
import com.example.colorpalettes.domain.model.ColorPalette
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getColorPalettes(): List<ColorPalette>
    suspend fun getLikeCount(objectId: String): ColorPalette
    suspend fun observeAddRelation(): Flow<RelationStatus?>
    suspend fun observeDeleteRelation(): Flow<RelationStatus?>
    suspend fun observeApproval(): Flow<ColorPalette>
    suspend fun observeDeletedPalettes(): Flow<ColorPalette>
    suspend fun checkSavedPalette(paletteObjectId: String, userObjectId: String): List<ColorPalette>
    suspend fun saveColorPalette(paletteObjectId: String, userObjectId: String): Int
    suspend fun deleteColorPalette(paletteObjectId: String, userObjectId: String): Int
}