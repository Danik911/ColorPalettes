package com.example.colorpalettes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.colorpalettes.presentation.screens.home.HomeScreen
import com.example.colorpalettes.presentation.screens.login.LoginScreen

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
        composable(route = Screen.Details.route) {}
        composable(route = Screen.Saved.route) {}
        composable(route = Screen.Submit.route) {}
        composable(route = Screen.Create.route) {}
    }
}