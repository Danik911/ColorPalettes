package com.example.colorpalettes.presentation.screens.detail

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.ui.theme.Gray700
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreen(
    navController: NavHostController,
    colorPalette: ColorPalette,
    showFab: Boolean,
    detailViewModel: DetailViewModel = hiltViewModel()
) {

    val isSaved = detailViewModel.isSaved
    val selectedPalette = detailViewModel.selectedPalette

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = colorPalette) {
        detailViewModel.updateSelectedPalette(colorPalette = colorPalette)
    }
    LaunchedEffect(key1 = Unit) {
        detailViewModel.uiEvent.collectLatest { detailScreenUiEvent ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = detailScreenUiEvent.message
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailTopBar(
                isSaved = isSaved,
                onBackClicked = { navController.popBackStack() },
                onSaveClicked = {
                    detailViewModel.saveColorPalette()
                }
            )


        },
        content = {
            DetailsContent(
                colorPalette = selectedPalette,
                onColorClicked = {}
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                backgroundColor = Gray700,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = " Heart Icon",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "${selectedPalette.totalLikes ?: 0}",
                        color = Color.White
                    )
                },
                onClick = {
                    detailViewModel.addLike()
                }
            )
        }
    )
}