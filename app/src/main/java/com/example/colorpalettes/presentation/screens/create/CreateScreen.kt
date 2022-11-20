package com.example.colorpalettes.presentation.screens.create

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.colorpalettes.presentation.components.ColorPicker

@Composable
fun CreateScreen(
    navHostController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {},
        content = {
            ColorPicker(
                selectedColor = {

                }
            )
        }
    )


}