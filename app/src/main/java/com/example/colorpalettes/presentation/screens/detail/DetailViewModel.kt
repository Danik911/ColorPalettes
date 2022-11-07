package com.example.colorpalettes.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var isSaved by mutableStateOf(false)
        private set

    var selectedPalette by mutableStateOf(ColorPalette())
        private set

    private val _uiEvent = Channel<DetailScreenUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun updateSelectedPalette(colorPalette: ColorPalette) {
        selectedPalette = colorPalette
        checkSavedPalette(colorPalette.objectId!!)
    }

    private fun checkSavedPalette(objectId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkSavedPalette(
                paletteObjectId = objectId,
                userObjectId = Backendless.UserService.CurrentUser().objectId
            )
            isSaved = result.any { it.objectId == objectId }
        }
    }
}

sealed class DetailScreenUiEvent(val message: String) {
    object AddLike : DetailScreenUiEvent(message = "Liked!")
    object RemoveLike : DetailScreenUiEvent(message = "Like is Removed!")
    object SavePalette : DetailScreenUiEvent(message = "Saved!")
    object RemoveSavedPalette : DetailScreenUiEvent(message = "Pallet is removed!")
    data class Error(val text: String) : DetailScreenUiEvent(message = text)
}