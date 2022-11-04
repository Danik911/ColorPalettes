package com.example.colorpalettes.presentation.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var colorPalettes = mutableStateListOf<ColorPalette>()
        private set

    init {
        getColorPalettes()
    }

    private fun getColorPalettes() {
        viewModelScope.launch(Dispatchers.IO) {
            colorPalettes.addAll(repository.getColorPalettes())
        }
    }
}