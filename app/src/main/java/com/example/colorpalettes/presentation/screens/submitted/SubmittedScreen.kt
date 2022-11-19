package com.example.colorpalettes.presentation.screens.submitted

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.colorpalettes.presentation.components.NavigationDrawer
import kotlinx.coroutines.launch

@Composable
fun SubmittedScreen(
    navController: NavHostController,
    submittedViewModel: SubmittedViewModel = hiltViewModel()
) {
    val submittedPalettes = submittedViewModel.submittedPalettes
    val requestState = submittedViewModel.requestState

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SubmittedTopBar(
                submittedColors = submittedPalettes,
                requestState = requestState,
                onSubmitClicked = { },
                onMenuClicked = { scope.launch { scaffoldState.drawerState.open() } })


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
        content = {}
    )
}