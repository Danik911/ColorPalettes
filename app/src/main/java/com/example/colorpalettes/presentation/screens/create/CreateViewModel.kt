package com.example.colorpalettes.presentation.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.repository.Repository
import com.example.colorpalettes.presentation.parseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _uiEvent = Channel<CreateUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun submitColorPalette(colorPalette: ColorPalette) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.submitColorPalette(colorPalette = colorPalette)
                _uiEvent.send(CreateUiEvent.Submitted)
            } catch (e: Exception) {
                _uiEvent.send(CreateUiEvent.Error(error = e.message?.parseError().toString()))
            }
        }
    }
}

sealed class CreateUiEvent(val message: String) {
    object Submitted : CreateUiEvent(message = "Successfully Submitted")
    data class Error(val error: String) : CreateUiEvent(message = error)
}