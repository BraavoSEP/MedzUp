package com.medzup.app

import android.app.Application
import com.medzup.app.managers.LanguageManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MedzUpApplication : Application() {

    @Inject
    lateinit var languageManager: LanguageManager

    override fun onCreate() {
        super.onCreate()
        // O LanguageManager agora lida com a inicialização e aplicação
        // do idioma correto (português como padrão ou o salvo pelo usuário).
        languageManager.initialize()
    }
} 