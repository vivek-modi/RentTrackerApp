package com.example.renttrackerapp

import com.example.renttrackerapp.modal.Result

sealed class UiState {
    data class OnSuccess(val result: List<Result>) : UiState()
    data class OnError(val message: Throwable) : UiState()
    data class IsLoading(val loading: Boolean) : UiState()
    object OnEmpty : UiState()
}
