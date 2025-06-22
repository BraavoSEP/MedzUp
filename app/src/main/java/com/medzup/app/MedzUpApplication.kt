package com.medzup.app

import android.app.Application
import android.content.res.Configuration
import android.os.Build
import com.medzup.app.managers.LanguageManager
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class MedzUpApplication : Application() {
    
    @Inject
    lateinit var languageManager: LanguageManager

    override fun onCreate() {
        super.onCreate()
        
        // Configurar idioma padrão como português
        setDefaultLocale()
        languageManager.initialize()
    }
    
    private fun setDefaultLocale() {
        val locale = Locale("pt")
        Locale.setDefault(locale)
        
        val config = Configuration(resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        
        resources.updateConfiguration(config, resources.displayMetrics)
    }
} 