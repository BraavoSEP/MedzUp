package com.medzup.app.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ManagersModule {
    // ReminderManager is automatically provided by Hilt due to @Inject annotation
} 