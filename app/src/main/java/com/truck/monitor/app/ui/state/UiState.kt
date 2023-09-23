package com.truck.monitor.app.ui.state

sealed class UiState {
    object OnStart : UiState()
    object OnLoading : UiState()
    data class OnSuccess<T>(val data: T) : UiState()
    data class OnFailure(val error: String, val errorDescription: String) : UiState()
}