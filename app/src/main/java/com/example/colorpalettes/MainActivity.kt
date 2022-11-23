package com.example.colorpalettes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.backendless.Backendless
import com.example.colorpalettes.navigation.SetupNavGraph
import com.example.colorpalettes.presentation.logout
import com.example.colorpalettes.ui.theme.ColorPalettesAppTheme
import com.example.colorpalettes.util.Constants.API_KEY
import com.example.colorpalettes.util.Constants.APP_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onResume() {
        super.onResume()
        logout(onSuccess = {}, onFailure = {})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Backendless.initApp(this, APP_ID, API_KEY)
        setContent {
            ColorPalettesAppTheme() {
                val navController = rememberNavController()
                SetupNavGraph(navHostController = navController)
            }
        }
    }
}