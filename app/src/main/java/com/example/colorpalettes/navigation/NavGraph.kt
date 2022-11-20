package com.example.colorpalettes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.presentation.saved.SavedScreen
import com.example.colorpalettes.presentation.screens.create.CreateScreen
import com.example.colorpalettes.presentation.screens.detail.DetailScreen
import com.example.colorpalettes.presentation.screens.home.HomeScreen
import com.example.colorpalettes.presentation.screens.login.LoginScreen
import com.example.colorpalettes.presentation.screens.submitted.SubmittedScreen
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
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument(
                    name = "showFab",
                    builder = {
                        type = NavType.BoolType
                        defaultValue = true
                    }
                )
            )
        ) {
            val savedPalette =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<ColorPalette>(
                    key = COLOR_PALETTE_KEY
                )
            if (savedPalette != null) {
                DetailScreen(
                    navController = navHostController,
                    colorPalette = savedPalette,
                    showFab = it.arguments?.getBoolean("showFab") ?: true
                )
            }
        }
        composable(route = Screen.Saved.route) {
            SavedScreen(navController = navHostController)
        }
        composable(route = Screen.Submit.route) {
            SubmittedScreen(navController = navHostController)
        }
        composable(route = Screen.Create.route) {
            CreateScreen(navHostController = navHostController)
        }
    }
}