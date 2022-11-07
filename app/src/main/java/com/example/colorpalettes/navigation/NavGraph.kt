package com.example.colorpalettes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.presentation.screens.detail.DetailScreen
import com.example.colorpalettes.presentation.screens.home.HomeScreen
import com.example.colorpalettes.presentation.screens.login.LoginScreen
import com.example.colorpalettes.util.Constants.COLOR_PALETTE_KEY

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navHostController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navHostController)
        }
        composable(route = Screen.Details.route) {
            val savedPalette =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<ColorPalette>(
                    key = COLOR_PALETTE_KEY
                )
            if (savedPalette != null) {
                DetailScreen(
                    navController = navHostController,
                    colorPalette = savedPalette,
                    showFab = true
                )
            }
        }
        composable(route = Screen.Saved.route) {}
        composable(route = Screen.Submit.route) {}
        composable(route = Screen.Create.route) {}
    }
}