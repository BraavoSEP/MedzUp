package com.medzup.app.ui.screens.add_medicine

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.medzup.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicineScreen(
    navController: NavController,
    viewModel: AddMedicineViewModel = hiltViewModel()
) {
    val medicine by viewModel.medicine.collectAsState()
    var medicineName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var intervalHours by remember { mutableStateOf("") }
    var durationDays by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Preencher campos se for edição
    LaunchedEffect(medicine) {
        medicine?.let {
            medicineName = it.name
            dosage = it.dosage
            stock = it.stock.toString()
            startTime = it.startTime
            intervalHours = it.intervalHours.toString()
            durationDays = it.durationDays.toString()
        }
    }

    val isEditing = medicine != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (isEditing) stringResource(id = R.string.edit_medicine_title) else stringResource(id = R.string.add_medicine_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.action_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = medicineName,
                onValueChange = { medicineName = it },
                label = { Text(stringResource(id = R.string.medicine_name_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text(stringResource(id = R.string.dosage_label_input)) },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text(stringResource(id = R.string.stock_label_input)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text(stringResource(id = R.string.start_time_label)) },
                placeholder = { Text("HH:mm") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = intervalHours,
                onValueChange = { intervalHours = it },
                label = { Text(stringResource(id = R.string.interval_hours_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = durationDays,
                onValueChange = { durationDays = it },
                label = { Text(stringResource(id = R.string.treatment_duration_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.saveMedicine(
                        name = medicineName,
                        dosage = dosage,
                        stock = stock,
                        startTime = startTime,
                        intervalHours = intervalHours,
                        durationDays = durationDays,
                        onSuccess = {
                            Toast.makeText(context, if (isEditing) R.string.medicine_updated_toast else R.string.medicine_saved_toast, Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isEditing) stringResource(id = R.string.update_medicine_button) else stringResource(id = R.string.save_medicine_button))
            }
        }
    }
}
