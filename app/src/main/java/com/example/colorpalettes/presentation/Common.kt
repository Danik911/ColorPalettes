package com.example.colorpalettes.presentation

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.util.Constants.CLIENT_ID
import com.example.colorpalettes.util.Constants.CLIENT_SECRET
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun StartActivityForResult(
    key: Any,
    onResultReceived: (String?) -> Unit,
    onDialogDismissed: (String) -> Unit,
    launcher: (ManagedActivityResultLauncher<Intent, ActivityResult>) -> Unit
) {
    val scope = rememberCoroutineScope()
    val activityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->

        try {
            if (activityResult.resultCode == Activity.RESULT_OK) {
                val result = activityResult.data?.let { intent ->
                    Auth.GoogleSignInApi.getSignInResultFromIntent(intent)

                }
                val serverAuthCode = result?.signInAccount?.serverAuthCode
                Log.d("StartActivityForResult", "ACCESSES TOKEN: $serverAuthCode")
                scope.launch(Dispatchers.IO) {
                    onResultReceived(getAccessToken(authCode = serverAuthCode))
                }
            } else {
                onDialogDismissed("Result is not OK")
                Log.d("StartActivityForResult", "RESULT IS NOT OK")
            }
        } catch (e: Exception) {
            onDialogDismissed(e.message.toString())
            Log.d("StartActivityForResult", "$e.message")
        }
    }
    LaunchedEffect(key1 = key) {
        launcher(activityLauncher)
    }
}

fun signIn(
    activity: Activity, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestServerAuthCode(CLIENT_ID)
        .build()
    val client = GoogleSignIn.getClient(activity, gso)

    launcher.launch(client.signInIntent)
}

fun logout(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
    Backendless.UserService.logout(
        object : AsyncCallback<Void> {
            override fun handleResponse(response: Void?) {
                onSuccess()
            }

            override fun handleFault(fault: BackendlessFault?) {
                onFailure(fault?.message.toString())
            }

        }
    )
}

private fun getAccessToken(authCode: String?): String? {
    val tokenResponse: GoogleTokenResponse = try {
        GoogleAuthorizationCodeTokenRequest(
            NetHttpTransport(),
            GsonFactory(),
            "https://www.googleapis.com/oauth2/v4/token",
            CLIENT_ID,
            CLIENT_SECRET,
            authCode,
            "https://api.backendless.com/525FEA11-7AAA-9C00-FFD9-7F595BFD9900/53C45395-CAE6-4262-8039-3D2D6526ED38/users/oauth/googleplus/authorize"
        ).execute()
    } catch (e: Exception) {
        Log.d("getAccessToken", e.message.toString())
        return null
    }
    return tokenResponse.accessToken
}

fun extractColor(colorPalette: ColorPalette?): List<String> {
    val colors = mutableListOf<String>()
    colorPalette?.colors?.split(",")?.forEach {
        colors.add(it.trim())
    }
    return colors
}

fun hexToColor(colorHex: String): Color {
    return try {
        Color(("FF" + colorHex.removePrefix("#")).toLong(16))
    } catch (e: Exception) {
        Color("FFFFFFFF".toLong(16))
    }
}

fun String.parseError(): String {
    return if (this.contains("No address associated with hostname")) {
        "No Internet connection"
    } else {
        this
    }
}









