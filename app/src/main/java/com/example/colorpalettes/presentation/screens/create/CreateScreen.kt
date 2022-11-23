package com.example.colorpalettes.presentation.screens.create

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.colorpalettes.domain.model.ColorPalette

@Composable
fun CreateScreen(
    navHostController: NavHostController,
    createViewModel: CreateViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        createViewModel.uiEvent.collect {
            scaffoldState.snackbarHostState.showSnackbar(
                message = it.message,
                actionLabel = "OK"
            )
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CreateTopBar {
                navHostController.popBackStack()
            }
        },
        content = {
            CreateContent(
                onSubmitClicked = {
                    createViewModel.submitColorPalette(
                        colorPalette = ColorPalette(colors = it)
                    )
                }
            )
        }
    )
}