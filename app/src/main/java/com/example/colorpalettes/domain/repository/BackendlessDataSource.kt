package com.example.colorpalettes.domain.repository

import com.example.colorpalettes.domain.model.ColorPalette

interface BackendlessDataSource{
    suspend fun getColorPalettes(): List<ColorPalette>
}