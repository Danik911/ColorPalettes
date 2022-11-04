package com.example.colorpalettes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.colorpalettes.domain.model.ColorPalette

@Composable
fun DefaultContent(
    navController: NavHostController,
    colorPalettes: List<ColorPalette>,
    showFab: Boolean = true
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(all = 6.dp)
    ) {
        items(
            items = colorPalettes,
            key = {
                it.objectId!!
            }
        ) {
            PaletteHolder(colorPalette = it, onClick = {})
        }
    }
}