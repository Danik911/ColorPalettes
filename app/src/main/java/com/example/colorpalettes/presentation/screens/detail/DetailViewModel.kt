package com.example.colorpalettes.presentation.screens.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
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

    fun saveColorPalette() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.saveColorPalette(
                    paletteObjectId = selectedPalette.objectId!!,
                    userObjectId = Backendless.UserService.CurrentUser().objectId
                )
                handleResult(result = result)
            } catch (e: Exception) {
                _uiEvent.send(
                    DetailScreenUiEvent.Error(
                        text = e.message?.parseError().toString()
                    )
                )
            }
        }
    }

    fun addLike(userObjectId: String = Backendless.UserService.CurrentUser().objectId) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.addLike(
                    paletteObjectId = selectedPalette.objectId!!,
                    userObjectId = userObjectId
                )
                if (result == 0) {
                    repository.removeLike(
                        paletteObjectId = selectedPalette.objectId!!,
                        userObjectId = userObjectId
                    )
                    selectedPalette = selectedPalette.totalLikes?.minus(1).let {
                        selectedPalette.copy(totalLikes = it)
                    }
                    _uiEvent.send(DetailScreenUiEvent.RemoveLike)
                } else if (result != null && result > 0) {
                    selectedPalette = selectedPalette.totalLikes?.plus(1).let {
                        selectedPalette.copy(totalLikes = it)
                    }
                    _uiEvent.send(
                        DetailScreenUiEvent.AddLike
                    )
                }
            } catch (e: Exception) {
                _uiEvent.send(
                    DetailScreenUiEvent.Error(
                        text = e.message?.parseError().toString()
                    )
                )
            }
        }
    }

    private suspend fun handleResult(result: Int) {
        if (result == 0) {
            repository.deleteColorPalette(
                paletteObjectId = selectedPalette.objectId!!,
                userObjectId = Backendless.UserService.CurrentUser().objectId
            )
            _uiEvent.send(DetailScreenUiEvent.RemoveSavedPalette)
            isSaved = false
        } else if (result > 0) {
            repository.saveColorPalette(
                paletteObjectId = selectedPalette.objectId!!,
                userObjectId = Backendless.UserService.CurrentUser().objectId
            )
            isSaved = true
            _uiEvent.send(DetailScreenUiEvent.SavePalette)
        }
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

    fun copyToClipboard(context: Context, color: String) {
        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Color", color)
        clipboardManager.setPrimaryClip(clipData)
        viewModelScope.launch {
            _uiEvent.send(DetailScreenUiEvent.CopyToClipboard(color))
        }

    }
}

sealed class DetailScreenUiEvent(val message: String) {
    object AddLike : DetailScreenUiEvent(message = "Liked!")
    object RemoveLike : DetailScreenUiEvent(message = "Like is Removed!")
    object SavePalette : DetailScreenUiEvent(message = "Saved!")
    object RemoveSavedPalette : DetailScreenUiEvent(message = "Palette is removed!")
    data class CopyToClipboard(val color: String) : DetailScreenUiEvent(message = "$color Copied!")
    data class Error(val text: String) : DetailScreenUiEvent(message = text)
}