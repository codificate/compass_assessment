package com.challenge.compass.di

import com.challenge.compass.domain.repository.PlainTextRepository
import com.challenge.compass.domain.usecases.PlainTextUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesPlainTextUseCase(repository: PlainTextRepository) =
        PlainTextUseCase(repository)
}