package com.myapp.voicehealth.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.myapp.voicehealth.ui.theme.Background

@Composable
fun SymptomInputScreen(navController:NavController,
    onFindDoctorClick: () -> Unit = {}
) {
    var inputText by remember { mutableStateOf("") }
    var isOnlineMode by remember { mutableStateOf(true) }
    var aiSuggestions by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        Text(
            text = "Describe your symptoms",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text("Enter your symptoms here...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 5
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* Trigger Voice Input */ }) {
                Icon(Icons.Filled.MailOutline, contentDescription = "Voice Input")
            }

            Button(onClick = {
                // Trigger LLM (online/offline) and update aiSuggestions
                aiSuggestions = listOf(
                    "You might have a mild fever",
                    "Recommended: Drink fluids and rest"
                )
            }) {
                Text("Analyze")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Home, contentDescription = null)
                Text(
                    text = if (isOnlineMode) "Online" else "Offline",
                    modifier = Modifier.padding(start = 4.dp)
                )
                Switch(
                    checked = isOnlineMode,
                    onCheckedChange = { isOnlineMode = it }
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "AI Suggestions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        if (aiSuggestions.isEmpty()) {
            Text("No suggestions yet. Please describe symptoms above.")
        } else {
            Column {
                aiSuggestions.forEach { suggestion ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = suggestion,
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFindDoctorClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("👨‍⚕️ Find Nearby Doctor")
        }
    }
}
