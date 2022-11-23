package com.example.colorpalettes.presentation.screens.login


import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.colorpalettes.navigation.Screen
import com.example.colorpalettes.presentation.StartActivityForResult
import com.example.colorpalettes.presentation.signIn
import timber.log.Timber

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val signInState = loginViewModel.signedInState
    val messageBarState = loginViewModel.messageBarState
    val activity = LocalContext.current as Activity

    Scaffold(
        topBar = {
            LoginTopBar()
        },
        content = {
            LoginContent(
                signedInState = signInState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    loginViewModel.updateSignedInState(signedIn = true)
                }
            )
        }
    )
    StartActivityForResult(
        key = signInState,
        onResultReceived = { accessToken ->
            Timber.d("$accessToken")
            Backendless.UserService.loginWithOAuth2(
                "googleplus",
                accessToken,
                mutableMapOf(
                    "email" to "email",
                    "given_name" to "first_name",
                    "family_name" to "last_name",
                    "picture" to "profile_photo"
                ),
                object : AsyncCallback<BackendlessUser> {
                    override fun handleResponse(response: BackendlessUser?) {
                        navController.popBackStack()
                        navController.navigate(Screen.Home.route)
                        Log.d("LoginScreen", "$response")
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        loginViewModel.updateSignedInState(signedIn = false)
                        fault?.let { loginViewModel.updateMessageBarState(message = it.message) }
                    }

                },
                false
            )

        },
        onDialogDismissed = {
            loginViewModel.updateSignedInState(signedIn = false)
            loginViewModel.updateMessageBarState(message = it)
        },
        launcher = { activityLauncher ->
            if (signInState) {
                signIn(activity = activity, launcher = activityLauncher)
            }
        }
    )
}