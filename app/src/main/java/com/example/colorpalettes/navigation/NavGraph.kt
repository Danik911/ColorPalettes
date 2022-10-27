package com.example.colorpalettes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route
    ) {
        composable(route = Screen.Login.route) {}
        composable(route = Screen.Home.route) {}
        composable(route = Screen.Details.route) {}
        composable(route = Screen.Saved.route) {}
        composable(route = Screen.Submit.route) {}
        composable(route = Screen.Create.route) {}
    }
}