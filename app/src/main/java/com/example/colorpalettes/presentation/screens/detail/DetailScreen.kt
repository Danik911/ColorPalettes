package com.example.colorpalettes.presentation.screens.detail

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.colorpalettes.domain.model.ColorPalette

@Composable
fun DetailScreen(
    navController: NavHostController,
    colorPalette: ColorPalette,
    showFab: Boolean
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailTopBar(
                isSaved = colorPalette.approved,
                onBackClicked = { navController.popBackStack() },
                onSaveClicked = {}
            )


        },
        content = {
            DetailsContent(
                colorPalette = colorPalette,
                onColorClicked = {}
            )
        },
        floatingActionButton = {}
    )
}