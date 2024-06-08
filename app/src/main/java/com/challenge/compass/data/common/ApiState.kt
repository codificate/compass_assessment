package com.challenge.compass.data.common

sealed class ApiState {
    data object Loading : ApiState()
    data class Success(val data: String): ApiState()
    data class Error(val exception: Throwable): ApiState()
}