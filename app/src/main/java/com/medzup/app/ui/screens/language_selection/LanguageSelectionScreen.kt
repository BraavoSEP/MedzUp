package com.medzup.app.ui.screens.language_selection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.medzup.app.R
import com.medzup.app.managers.LanguageManager

@Composable
fun LanguageSelectionScreen(
    onLanguageSelected: () -> Unit,
    viewModel: LanguageSelectionViewModel = hiltViewModel()
) {
    var selectedLanguage by remember { mutableStateOf(LanguageManager.LANGUAGE_PORTUGUESE) }

    // Aplica o idioma inicial
    LaunchedEffect(Unit) {
        viewModel.setLanguage(selectedLanguage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Logo/Title
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Title
        Text(
            text = stringResource(R.string.language_selection_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Subtitle
        Text(
            text = stringResource(R.string.language_selection_subtitle),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        
        // Language Options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Portuguese Option
            LanguageOption(
                title = stringResource(R.string.language_portuguese),
                isSelected = selectedLanguage == LanguageManager.LANGUAGE_PORTUGUESE,
                onClick = {
                    selectedLanguage = LanguageManager.LANGUAGE_PORTUGUESE
                    viewModel.setLanguage(selectedLanguage)
                }
            )
            
            // English Option
            LanguageOption(
                title = stringResource(R.string.language_english),
                isSelected = selectedLanguage == LanguageManager.LANGUAGE_ENGLISH,
                onClick = {
                    selectedLanguage = LanguageManager.LANGUAGE_ENGLISH
                    viewModel.setLanguage(selectedLanguage)
                }
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Continue Button
        Button(
            onClick = {
                viewModel.setFirstLaunchComplete()
                onLanguageSelected()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(R.string.language_continue),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun LanguageOption(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
} 