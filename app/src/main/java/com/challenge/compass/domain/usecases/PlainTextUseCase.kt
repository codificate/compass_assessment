package com.challenge.compass.domain.usecases

import com.challenge.compass.data.common.ApiState
import com.challenge.compass.domain.model.CompassState
import com.challenge.compass.domain.repository.PlainTextRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlainTextUseCase @Inject constructor(
    private val repository: PlainTextRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Flow<CompassState> {
        return repository.getPlainText()
            .map { result ->
                when (result) {
                    is ApiState.Error -> CompassState.Error(result.exception)//emit()
                    ApiState.Loading -> CompassState.Loading
                    is ApiState.Success -> CompassState.Success(result.data)
                }
            }
            .flowOn(context = dispatcher)
    }
}