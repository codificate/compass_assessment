package com.challenge.compass.presentation

sealed class UIState {
    data object Init: UIState()
    data object Error: UIState()
    data object Loading : UIState()
    data class Success(val everyXCh: String, val wordCounter: String): UIState()
}