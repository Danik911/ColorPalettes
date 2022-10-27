package com.example.colorpalettes.navigation

sealed class Screen(val route: String) {
    object Login : Screen(route = "login")
    object Home : Screen(route = "home")
    object Details : Screen(route = "details")
    object Saved : Screen(route = "saved")
    object Submit : Screen(route = "submit")
    object Create : Screen(route = "create")
}
