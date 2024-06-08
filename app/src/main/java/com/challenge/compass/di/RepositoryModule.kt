package com.challenge.compass.di

import com.challenge.compass.data.PlainTextDataSource
import com.challenge.compass.data.repository.PlainTextRepositoryImpl
import com.challenge.compass.data.service.CompassApiService
import com.challenge.compass.domain.repository.PlainTextRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesRepository(apiService: CompassApiService, dataSource: PlainTextDataSource): PlainTextRepository =
        PlainTextRepositoryImpl(apiService, dataSource)
}