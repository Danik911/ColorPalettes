package com.example.colorpalettes.presentation.saved

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.Backendless
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.model.RequestState
import com.example.colorpalettes.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _savedPalettes = mutableStateListOf<ColorPalette>()
    val savedPalettes: List<ColorPalette> = _savedPalettes

    var requestState by mutableStateOf<RequestState>(RequestState.Idle)
        private set

    init {
        getSavedPalettes()
        observeSavedPalettes()
    }

    private fun getSavedPalettes() {
        requestState = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = _savedPalettes.addAll(
                repository.getSavedPalettes(
                    userObjectId = Backendless.UserService.CurrentUser().objectId
                )
            )
            requestState = if (result) RequestState.Success else RequestState.Error
        }
    }

    private fun observeSavedPalettes() {
        viewModelScope.launch {
            repository.observeSavedPalettes(
                userObjectId = Backendless.UserService.CurrentUser().objectId
            ).collect { relationStatus ->
                _savedPalettes.removeAll {
                    it.objectId == relationStatus?.children?.first()
                }
            }
        }
    }


}