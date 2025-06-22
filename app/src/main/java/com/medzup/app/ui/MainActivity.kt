package com.medzup.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.medzup.app.managers.LanguageManager
import com.medzup.app.ui.navigation.MainNavGraph
import com.medzup.app.ui.screens.language_selection.LanguageSelectionScreen
import com.medzup.app.ui.theme.MedzUpTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var languageManager: LanguageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MedzUpTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent(languageManager)
                }
            }
        }
    }
}

@Composable
fun MainContent(languageManager: LanguageManager) {
    val showLanguageSelection = languageManager.isFirstLaunch()

    if (showLanguageSelection) {
        LanguageSelectionScreen(
            onLanguageSelected = {
                // A atividade será recriada automaticamente pelo setLanguage
            }
        )
    } else {
        MainNavGraph()
    }
}