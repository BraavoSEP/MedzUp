package com.medzup.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.medzup.app.managers.LanguageManager
import com.medzup.app.ui.navigation.MainNavGraph
import com.medzup.app.ui.screens.language_selection.LanguageSelectionScreen
import com.medzup.app.ui.theme.MedzUpTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var languageManager: LanguageManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Aplicar o idioma selecionado
        val updatedContext = languageManager.updateLocale(this)
        
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
    var showLanguageSelection by remember { mutableStateOf(languageManager.isFirstLaunch()) }
    
    if (showLanguageSelection) {
        LanguageSelectionScreen(
            onLanguageSelected = {
                showLanguageSelection = false
            }
        )
    } else {
        MainNavGraph()
    }
} 
} 