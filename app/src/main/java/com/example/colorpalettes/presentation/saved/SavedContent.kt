package com.example.colorpalettes.presentation.saved

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.model.RequestState
import com.example.colorpalettes.presentation.components.DefaultContent

@Composable
fun SavedContent(
    navController: NavHostController,
    requestState: RequestState,
    savedPalette: List<ColorPalette>
) {
    when (requestState) {
        is RequestState.Success -> {
            if (savedPalette.isEmpty()) {
                NoSavedPalettes()
            } else {
                DefaultContent(
                    navController = navController,
                    colorPalettes = savedPalette,
                    showFab = false
                )
            }
        }
        is RequestState.Error -> {
            if (savedPalette.isEmpty()) NoSavedPalettes()
        }
        else -> {}
    }
}

@Composable
fun NoSavedPalettes() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No saved palettes",
            style = TextStyle(
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}