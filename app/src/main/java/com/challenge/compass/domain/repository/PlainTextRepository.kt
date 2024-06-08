package com.challenge.compass.domain.repository

import com.challenge.compass.data.common.ApiState
import kotlinx.coroutines.flow.Flow

interface PlainTextRepository {
    suspend fun getPlainText(): Flow<ApiState>
}