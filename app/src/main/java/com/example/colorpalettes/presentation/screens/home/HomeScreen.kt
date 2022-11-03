package com.example.colorpalettes.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.presentation.components.NavigationDrawer
import com.example.colorpalettes.presentation.components.PaletteHolder
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {},
        drawerContent = {
            NavigationDrawer(
                navController = navController,
                scaffoldState = scaffoldState,
                logoutFailed = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Something went wrong",
                            actionLabel = "OK"
                        )
                    }
                }
            )
        },
        content = {
            PaletteHolder(
                colorPalette = ColorPalette(
                    objectId = "", approved = true,
                    colors = "#F1DDBF,#534E56,#5D4E56,#F3AC56", totalLikes = 10,
                ),
                onClick = {}
            )
        }
    )
}