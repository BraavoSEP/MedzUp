package com.medzup.app.ui.screens.add_medicine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.medzup.app.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    navController: NavController,
    viewModel: AddMedicineViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var reminderTimes by remember { mutableStateOf("") }

    val ocrText = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("ocr_text")?.observeAsState()

    LaunchedEffect(ocrText) {
        ocrText?.value?.let {
            name = it
            // Clear the value from savedStateHandle so it's not reused
            navController.currentBackStackEntry?.savedStateHandle?.set("ocr_text", null)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Medicine") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Medicine Name") },
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { navController.navigate(Screen.CameraScan.createRoute("box_name")) }) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Scan medicine box")
                }
            }
            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage (e.g., 1 pill)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = reminderTimes,
                onValueChange = { reminderTimes = it },
                label = { Text("Reminder Times (e.g., 08:00,20:00)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.addMedicine(
                        name = name,
                        dosage = dosage,
                        stock = stock.toIntOrNull() ?: 0,
                        reminderTimes = reminderTimes
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Medicine")
            }
        }
    }
} 