package com.example.colorpalettes.domain.model

sealed class RequestState{
    object Idle: RequestState()
    object Loading: RequestState()
    object Success: RequestState()
    object Error: RequestState()
}
