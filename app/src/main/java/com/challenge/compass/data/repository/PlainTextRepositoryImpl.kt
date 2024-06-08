package com.challenge.compass.data.repository

import com.challenge.compass.data.PlainTextDataSource
import com.challenge.compass.data.common.ApiState
import com.challenge.compass.data.service.CompassApiService
import com.challenge.compass.domain.repository.PlainTextRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlainTextRepositoryImpl @Inject constructor(
    private val apiService: CompassApiService,
    private val dataSource: PlainTextDataSource
) :
    PlainTextRepository {
    override suspend fun getPlainText(): Flow<ApiState> = flow {
        emit(ApiState.Loading)
        try {
            val response = apiService.getAbout()
            val data = response.body() ?: ""
            dataSource.saveString(data)
            emit(ApiState.Success(data))
        } catch (e: Exception) {
            dataSource.getString()?.let { data -> emit(ApiState.Success(data)) } ?: run {
                emit(ApiState.Error(Exception(e.localizedMessage ?: "Unknown error")))
            }
        }
    }
}