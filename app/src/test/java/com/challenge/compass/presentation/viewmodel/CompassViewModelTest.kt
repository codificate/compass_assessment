package com.challenge.compass.presentation.viewmodel

import com.challenge.compass.MainDispatcherRule
import com.challenge.compass.domain.model.CompassState
import com.challenge.compass.domain.usecases.PlainTextUseCase
import com.challenge.compass.presentation.UIState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isA
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CompassViewModelTest {

    @get:Rule
    val rule = MainDispatcherRule()

    private lateinit var viewModel: CompassViewModel
    private lateinit var mockUseCase: PlainTextUseCase

    @Before
    fun setUp() {
        mockUseCase = mockk<PlainTextUseCase>()
        viewModel = CompassViewModel(mockUseCase)
    }

    @Test
    fun `fetchData fetches data and emits Success state`() = runTest {
        val testData = "This is some sample HTML content"
        val flowState = MutableStateFlow<CompassState>(CompassState.Loading)
        coEvery { mockUseCase.invoke() } returns flowState

        viewModel.fetchData()
        val capturedState = async {
            viewModel.compassState.last()
        }
        flowState.emit(CompassState.Success(testData))

        assertThat(capturedState.await(), isA(UIState.Success::class.java))
    }
}