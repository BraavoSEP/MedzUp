package com.medzup.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.medzup.app.ui.navigation.MainNavGraph
import com.medzup.app.ui.theme.MedzUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedzUpTheme {
                MainNavGraph()
            }
        }
    }
}