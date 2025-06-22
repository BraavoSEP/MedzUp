package com.medzup.app.di

import android.content.Context
import com.medzup.app.managers.LanguageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagersModule {

    @Provides
    @Singleton
    fun provideLanguageManager(@ApplicationContext context: Context): LanguageManager {
        return LanguageManager(context)
    }
} 