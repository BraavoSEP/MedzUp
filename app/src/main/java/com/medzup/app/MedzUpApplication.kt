package com.medzup.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MedzUpApplication : Application() {
    // A lógica de inicialização do LanguageManager foi removida,
    // pois o aplicativo agora é exclusivamente em português.
} 