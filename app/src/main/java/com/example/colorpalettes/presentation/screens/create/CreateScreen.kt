package com.example.colorpalettes.presentation.screens.create

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun CreateScreen(
    navHostController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()

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

                }
            )
        }
    )
}