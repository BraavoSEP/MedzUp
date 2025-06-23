package com.medzup.app.ui.screens.patient_medicine_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.medzup.app.R
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.ui.navigation.Screen
import com.medzup.app.ui.screens.home.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientMedicineListScreen(
    navController: NavController,
    viewModel: PatientMedicineListViewModel = hiltViewModel()
) {
    val patient by viewModel.patient.collectAsState()
    val medicines by viewModel.medicines.collectAsState()
    val showDeletePatientDialog = remember { mutableStateOf(false) }
    val medicineToDelete = remember { mutableStateOf<MedicineEntity?>(null) }

    if (patient == null) {
        // Exibe mensagem de erro amigável se o paciente não for encontrado
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.error_patient_not_found)) },
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
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(id = R.string.error_patient_not_found), color = MaterialTheme.colorScheme.error)
            }
        }
        return
    }

    if (showDeletePatientDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeletePatientDialog.value = false },
            title = { Text("Excluir paciente") },
            text = { Text("Tem certeza que deseja excluir este paciente? Todos os medicamentos associados também serão removidos.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePatient {
                        showDeletePatientDialog.value = false
                        navController.popBackStack()
                    }
                }) { Text("Excluir") }
            },
            dismissButton = {
                TextButton(onClick = { showDeletePatientDialog.value = false }) { Text("Cancelar") }
            }
        )
    }

    if (medicineToDelete.value != null) {
        AlertDialog(
            onDismissRequest = { medicineToDelete.value = null },
            title = { Text("Excluir medicamento") },
            text = { Text("Tem certeza que deseja excluir o medicamento '${medicineToDelete.value?.name}'?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteMedicine(medicineToDelete.value!!.id) {
                        medicineToDelete.value = null
                    }
                }) { Text("Excluir") }
            },
            dismissButton = {
                TextButton(onClick = { medicineToDelete.value = null }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = patient?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.action_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showDeletePatientDialog.value = true }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Excluir paciente")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    patient?.let {
                        navController.navigate(Screen.AddMedicine.createRoute(it.id))
                    }
                },
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_medicine_title)
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (medicines.isEmpty()) {
                EmptyState(message = stringResource(id = R.string.empty_medicines_message))
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(medicines) { medicine ->
                        MedicineCard(
                            medicine = medicine,
                            onClick = {
                                navController.navigate(Screen.MedicineDetails.createRoute(medicine.id))
                            },
                            onDelete = { medicineToDelete.value = it }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineCard(medicine: MedicineEntity, onClick: () -> Unit, onDelete: (MedicineEntity) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = medicine.name, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = stringResource(R.string.dosage_label, medicine.dosage),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.stock_label, medicine.stock),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.schedule_label, medicine.startTime, medicine.intervalHours),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = { onDelete(medicine) }) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Excluir medicamento")
            }
        }
    }
} 