package com.challenge.compass.domain.model

sealed class CompassState {
    data object Loading: CompassState()
    data class Success(val data: String): CompassState()
    data class Error(val exception: Throwable): CompassState()
}