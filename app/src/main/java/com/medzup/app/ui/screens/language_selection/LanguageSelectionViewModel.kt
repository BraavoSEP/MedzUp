package com.medzup.app.ui.screens.language_selection

import androidx.lifecycle.ViewModel
import com.medzup.app.managers.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageSelectionViewModel @Inject constructor(
    private val languageManager: LanguageManager
) : ViewModel() {
    
    fun setLanguage(languageCode: String) {
        languageManager.setLanguage(languageCode)
    }
    
    fun setFirstLaunchComplete() {
        languageManager.setFirstLaunchComplete()
    }
} 