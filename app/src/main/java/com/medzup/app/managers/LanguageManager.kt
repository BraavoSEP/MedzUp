package com.medzup.app.managers

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
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
        applyLanguage(languageCode)
    }
    
    fun applyLanguage(languageCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
    
    fun initialize() {
        val currentLanguage = getCurrentLanguage()
        applyLanguage(currentLanguage)
    }
} 