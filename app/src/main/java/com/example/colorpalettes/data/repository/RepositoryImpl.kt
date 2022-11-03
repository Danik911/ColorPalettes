package com.example.colorpalettes.data.repository

import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.repository.BackendlessDataSource
import com.example.colorpalettes.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val backendlessDataSource: BackendlessDataSource
) : Repository {
    override suspend fun getColorPalettes(): List<ColorPalette> {
        return backendlessDataSource.getColorPalettes()
    }

}