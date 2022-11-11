package com.example.colorpalettes.presentation.saved

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.colorpalettes.presentation.components.NavigationDrawer
import kotlinx.coroutines.launch

@Composable
fun SavedScreen(
    navController: NavHostController,
    savedViewModel: SavedViewModel = hiltViewModel()
) {
    val savedPalettes = savedViewModel.savedPalettes
    val requestState = savedViewModel.requestState

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SavedTopBar(
                onMenuClicked = {
                    scope.launch { scaffoldState.drawerState.open() }
                }
            )
        },
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
            SavedContent(
                navController = navController,
                requestState = requestState,
                savedPalette = savedPalettes
            )
        }
    )
}