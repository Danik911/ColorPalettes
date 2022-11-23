package com.example.colorpalettes.navigation

import com.example.colorpalettes.util.Constants.SIGNED_IN_STATE

sealed class Screen(val route: String) {
    object Login : Screen(route = "login/{$SIGNED_IN_STATE}"){
        fun passSingedInState(signedInState: Boolean = true) = "login/$signedInState"
    }
    object Home : Screen(route = "home")
    object Details : Screen(route = "details/{showFab}"){
        fun passShowFab(showFab: Boolean = true) = "details/$showFab"
    }
    object Saved : Screen(route = "saved")
    object Submit : Screen(route = "submit")
    object Create : Screen(route = "create")
}
