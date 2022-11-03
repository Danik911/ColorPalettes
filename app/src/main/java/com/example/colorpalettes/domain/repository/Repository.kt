package com.example.colorpalettes.domain.repository

import com.example.colorpalettes.domain.model.ColorPalette

interface Repository {
    suspend fun getColorPalettes(): List<ColorPalette>
}