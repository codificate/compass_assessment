package com.challenge.compass.di

import android.content.Context
import com.challenge.compass.data.PlainTextDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun providePlainTextDataSource(context: Context): PlainTextDataSource {
        return PlainTextDataSource(context.getSharedPreferences("compass_preferences", Context.MODE_PRIVATE))
    }
}