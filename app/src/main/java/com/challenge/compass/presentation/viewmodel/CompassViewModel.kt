package com.challenge.compass.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.compass.domain.model.CompassState
import com.challenge.compass.domain.usecases.PlainTextUseCase
import com.challenge.compass.presentation.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject

@HiltViewModel
class CompassViewModel @Inject constructor(private val useCase: PlainTextUseCase) : ViewModel() {

    private val internalState = MutableStateFlow<UIState>(UIState.Init)
    val compassState: StateFlow<UIState> = internalState

    fun fetchData() {
        viewModelScope.launch {
            try {
                useCase()
                    .collectLatest { result ->
                        when (result) {
                            is CompassState.Success -> {
                                val htmlString = result.data.trim()
                                val xthCharacter = setXthCharacters(htmlString)
                                val numberWords = setWordNumbers(htmlString)
                                internalState.value = UIState.Success(xthCharacter, numberWords)
                            }

                            is CompassState.Error -> {
                                // Handle error state from useCase (e.g., display error message)
                                Log.e("fetchData", "Error fetching data: ${result.exception}")
                                internalState.value = UIState.Error
                            }

                            is CompassState.Loading -> {
                                // Handle loading state (e.g., show a progress indicator)
                                // (Optional, depending on your UI needs)
                                Log.e("fetchData", "Loading")
                                internalState.value = UIState.Loading
                            }
                        }
                    }
            } catch (e: Exception) {
                Log.e("fetchData", "Unexpected error", e) // Example using Logcat
                internalState.value = UIState.Error
            }
        }
    }

    private fun setWordNumbers(htmlString: String): String {
        return try {
            val document: Document = Jsoup.parse(htmlString)
            val wordNumber =
                document.body().allElements.eachText().sumOf { text ->
                    text.replace(' ', ',').trim().split(',').size
                }
            wordNumber.toString()
        } catch (e: Exception) {
            Log.e(
                "fetchData",
                "Error parsing HTML",
                e
            )
            ""
        }
    }

    private fun setXthCharacters(htmlString: String): String {
        val characterBuilder = StringBuilder()
        for (i in 9 until htmlString.length step 10) {
            characterBuilder.append(htmlString[i])
            characterBuilder.append(',')
        }
        return characterBuilder.toString()
    }
}