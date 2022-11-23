package com.example.colorpalettes.presentation.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.colorpalettes.domain.model.MessageBarState
import com.example.colorpalettes.util.Constants.SIGNED_IN_STATE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var signedInState by mutableStateOf(
        value = savedStateHandle.get<Boolean>(SIGNED_IN_STATE) ?: true
    )
        private set

    var messageBarState by mutableStateOf(MessageBarState())
        private set

    fun updateSignedInState(signedIn: Boolean) {
        signedInState = signedIn
    }

    fun updateMessageBarState(message: String) {
        messageBarState = MessageBarState(error = Exception(message))
    }
}
