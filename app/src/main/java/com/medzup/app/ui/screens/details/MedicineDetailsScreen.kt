package com.medzup.app.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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

    val scannedBulaText = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("bula_text")?.observeAsState()

    LaunchedEffect(scannedBulaText) {
        scannedBulaText?.value?.let {
            bulaText = it
            navController.currentBackStackEntry?.savedStateHandle?.set("bula_text", null)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(medicine?.name ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                MedicineInfoSection(
                    medicine = med,
                    navController = navController,
                    onDoseRecorded = { status ->
                        viewModel.recordDose(status)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                DoseHistorySection(doseHistory)
                bulaText?.let {
                    Spacer(modifier = Modifier.height(16.dp))
                    BulaSection(
                        text = it,
                        simplifiedText = simplifiedBula,
                        onSimplifyClick = { viewModel.simplifyBulaText(it) }
                    )
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
            Text("Dosage: ${medicine.dosage}", style = MaterialTheme.typography.bodyLarge)
            Text("Stock: ${medicine.stock}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onDoseRecorded("TAKEN") }) {
                    Text("Mark as Taken")
                }
                OutlinedButton(onClick = { onDoseRecorded("SKIPPED") }) {
                    Text("Mark as Skipped")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate(Screen.CameraScan.createRoute("bula")) }) {
                Text("Scan Bula")
            }
        }
    }
}

@Composable
fun DoseHistorySection(history: List<DoseHistoryEntity>) {
    Text("History", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn {
        items(history) { record ->
            val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(Date(record.timestamp))
            Text("• ${record.status} at $date")
        }
    }
}

@Composable
fun BulaSection(text: String, simplifiedText: String?, onSimplifyClick: () -> Unit) {
    Text("Scanned Bula Text", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(8.dp))
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(text)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = onSimplifyClick) {
        Text("Simplify Text")
    }
    simplifiedText?.let {
        Spacer(modifier = Modifier.height(16.dp))
        Text("Simplified Version", style = MaterialTheme.typography.titleLarge)
        Card(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(it)
            }
        }
    }
} 