package com.medzup.app.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.medzup.app.R
import com.medzup.app.data.database.model.DoseHistoryEntity
import com.medzup.app.data.database.model.MedicineEntity
import com.medzup.app.ui.navigation.Screen
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineDetailsScreen(
    navController: NavController,
    viewModel: MedicineDetailsViewModel = hiltViewModel()
) {
    val medicine by viewModel.medicine.collectAsState()
    val doseHistory by viewModel.doseHistory.collectAsState()
    val simplifiedBula by viewModel.simplifiedBula.collectAsState()
    var bulaText by remember { mutableStateOf<String?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val scannedBulaText = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("bula_text")?.observeAsState()

    LaunchedEffect(scannedBulaText) {
        scannedBulaText?.value?.let {
            bulaText = it
            navController.currentBackStackEntry?.savedStateHandle?.set("bula_text", null)
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Excluir medicamento") },
            text = { Text("Tem certeza que deseja excluir este medicamento?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteMedicine {
                        showDeleteDialog = false
                        navController.popBackStack()
                    }
                }) { Text("Excluir") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancelar") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.details_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.action_back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Navegar para tela de edição
                        medicine?.let {
                            navController.navigate(
                                com.medzup.app.ui.navigation.Screen.EditMedicine.createRoute(it.patientId, it.id)
                            )
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Excluir")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        medicine?.let { med ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    MedicineInfoSection(
                        medicine = med,
                        navController = navController,
                        onDoseRecorded = { status ->
                            viewModel.recordDose(status)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    FutureAlarmsSection(med)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item {
                    DoseHistorySection(doseHistory)
                }
                item {
                    bulaText?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        BulaSection(
                            text = it,
                            simplifiedText = simplifiedBula,
                            onSimplifyClick = { viewModel.simplifyBulaText() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicineInfoSection(
    medicine: MedicineEntity,
    navController: NavController,
    onDoseRecorded: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(stringResource(id = R.string.dosage_label_details, medicine.dosage), style = MaterialTheme.typography.bodyLarge)
            Text(stringResource(id = R.string.stock_label_details, medicine.stock), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onDoseRecorded("TAKEN") }) {
                    Text(stringResource(id = R.string.mark_as_taken))
                }
                OutlinedButton(onClick = { onDoseRecorded("SKIPPED") }) {
                    Text(stringResource(id = R.string.mark_as_skipped))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate(Screen.CameraScan.createRoute("bula")) }) {
                Text(stringResource(id = R.string.scan_bula))
            }
        }
    }
}

@Composable
fun DoseHistorySection(history: List<DoseHistoryEntity>) {
    Text(stringResource(id = R.string.history), style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(8.dp))
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        history.forEach { record ->
            val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(Date(record.timestamp))
            Text("\u2022 ${record.status} at $date")
        }
    }
}

@Composable
fun BulaSection(text: String, simplifiedText: String?, onSimplifyClick: () -> Unit) {
    Text(stringResource(id = R.string.scanned_bula_text), style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(text)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = onSimplifyClick) {
        Text(stringResource(id = R.string.simplify_text))
    }
    simplifiedText?.let {
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(id = R.string.simplified_version), style = MaterialTheme.typography.titleLarge)
        Card(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(it)
            }
        }
    }
}

@Composable
fun FutureAlarmsSection(medicine: MedicineEntity) {
    val formatter = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }
    val timeParts = medicine.startTime.split(":").map { it.trim().toInt() }
    if (timeParts.size == 2) {
        val startHour = timeParts[0]
        val startMinute = timeParts[1]
        val interval = medicine.intervalHours
        val duration = medicine.durationDays

        val now = Calendar.getInstance()
        val treatmentEnd = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, duration) }
        val currentReminderTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, startHour)
            set(Calendar.MINUTE, startMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (currentReminderTime.before(now)) {
            while (currentReminderTime.before(now)) {
                currentReminderTime.add(Calendar.HOUR_OF_DAY, interval)
            }
        }
        val futureTimes = mutableListOf<String>()
        while (currentReminderTime.before(treatmentEnd)) {
            futureTimes.add(formatter.format(currentReminderTime.time))
            currentReminderTime.add(Calendar.HOUR_OF_DAY, interval)
        }
        if (futureTimes.isNotEmpty()) {
            Text("Próximos horários agendados:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            futureTimes.forEach { time ->
                Text("• $time", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
} 