package com.example.colorpalettes.presentation.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backendless.rt.data.RelationStatus
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
        observeAddRelation()
        observeDeleteRelation()
    }

    private fun getColorPalettes() {
        viewModelScope.launch(Dispatchers.IO) {
            colorPalettes.addAll(repository.getColorPalettes())
        }
    }

    private fun observeAddRelation() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeAddRelation().collect { status ->
                updateNumberOfLikes(relationStatus = status)
            }
        }
    }
    private fun observeDeleteRelation(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.observeDeleteRelation().collect{status->
                updateNumberOfLikes(relationStatus = status)
            }
        }
    }

    private suspend fun updateNumberOfLikes(relationStatus: RelationStatus?) {
        val observePalette =
            relationStatus?.parentObjectId?.let { repository.getLikeCount(objectId = it) }

        var position = 0
        var palette = ColorPalette()
        colorPalettes.forEachIndexed { index, colorPalette ->
            if (colorPalette.objectId == relationStatus?.parentObjectId) {
                position = index
                palette = colorPalette
            }
        }

        colorPalettes.set(
            index = position,
            element = palette.copy(totalLikes = observePalette?.totalLikes)
        )
    }
}