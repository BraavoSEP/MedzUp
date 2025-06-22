package com.medzup.app.managers

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "language_prefs"
        private const val KEY_LANGUAGE = "selected_language"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        
        const val LANGUAGE_PORTUGUESE = "pt"
        const val LANGUAGE_ENGLISH = "en"
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean(KEY_FIRST_LAUNCH, true)
    }
    
    fun setFirstLaunchComplete() {
        prefs.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
    }
    
    fun getCurrentLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, LANGUAGE_PORTUGUESE) ?: LANGUAGE_PORTUGUESE
    }
    
    fun setLanguage(languageCode: String) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }
    
    fun updateLocale(context: Context): Context {
        val language = getCurrentLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)
        
        val config = Configuration(context.resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        
        return context.createConfigurationContext(config)
    }
    
    fun updateResources(resources: Resources): Resources {
        val language = getCurrentLanguage()
        val locale = Locale(language)
        Locale.setDefault(locale)
        
        val config = Configuration(resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        
        return resources.createConfigurationContext(config).resources
    }
} 