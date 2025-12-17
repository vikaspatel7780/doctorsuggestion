package com.myapp.voicehealth.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.myapp.voicehealth.domain.models.HealthRecord
import com.myapp.voicehealth.ui.components.CommonTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthHistoryScreen(navController: NavController) {
    val dummyRecords = remember {
        listOf(
            HealthRecord("CBC Test", "2025-07-12", "All values are normal."),
            HealthRecord("HIV Test", "2025-07-10", "Negative."),
            HealthRecord("X-Ray Chest", "2025-06-25", "Mild bronchitis signs."),
            HealthRecord("Blood Sugar", "2025-06-10", "Slightly high fasting glucose.")
        )
    }

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "My Record History",
                onBack = { navController.popBackStack() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navigate to Add Record screen */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Record")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyRecords) { record ->
                HealthRecordCard(record)
            }
        }
    }
}

