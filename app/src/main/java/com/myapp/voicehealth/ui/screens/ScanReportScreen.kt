package com.myapp.voicehealth.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.myapp.voicehealth.domain.models.HealthRecord
import com.myapp.voicehealth.ui.components.CommonTopBar
import com.myapp.voicehealth.ui.components.buildCbcGeminiPrompt
import com.myapp.voicehealth.ui.components.extractCbcFromOcr
import com.myapp.voicehealth.ui.components.extractTextFromImage
import com.myapp.voicehealth.ui.components.mockSymptomAnalysis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanReportScreen(navController: NavController) {

    val context = LocalContext.current

    val selectedReportType = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val extractedText = remember { mutableStateOf("") }
    val result = remember { mutableStateOf("") }

    val bloodReportTypes = listOf(
        "Complete Blood Count (CBC)",
        "Hemogram",
        "Blood Sugar Test",
        "Fasting Blood Sugar (FBS)",
        "Post Prandial Blood Sugar (PPBS)",
        "Random Blood Sugar (RBS)",
        "HbA1c (Glycated Hemoglobin)"
    )

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> imageUri.value = uri }

    Scaffold(
        topBar = {
            CommonTopBar(
                title = "Scan Medical Report",
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🧪 STEP 1: SELECT REPORT TYPE
            Text(
                text = "Select Report Type",
                style = MaterialTheme.typography.titleMedium
            )

            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
            ) {
                TextField(
                    value = selectedReportType.value,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Choose medical report type") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded.value)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    bloodReportTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                selectedReportType.value = type
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            // 📤 STEP 2: UPLOAD BUTTON (Enabled only after selection)
            Button(
                onClick = { imageLauncher.launch("image/*") },
                enabled = selectedReportType.value.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Upload Medical Report Image")
            }

            // 🖼 IMAGE PREVIEW
            imageUri.value?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                // 🔍 OCR BUTTON
                Button(
                    onClick = {
                        extractTextFromImage(context, uri) {
                            val cbcResults = extractCbcFromOcr(it)
                            val data = buildCbcGeminiPrompt(cbcResults)
                            result.value = data
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Extract Text")
                }
            }
            Log.d("extractedText","extractedText $extractedText")

            // 📄 OCR TEXT
            if (extractedText.value.isNotEmpty()) {
                Text("Extracted Text", style = MaterialTheme.typography.titleMedium)

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(120.dp, 220.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(extractedText.value)
                }

                // 🧠 ANALYZE BUTTON
                Button(
                    onClick = {
                        result.value = mockSymptomAnalysis(
                            selectedReportType.value,
                            extractedText.value
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Analyze Report")
                }
            }

            // 🤖 AI RESULT
            if (result.value.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = result.value,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Text(
                    text = "⚠️ For educational purposes only. Not a medical diagnosis.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red
                )
            }
        }
    }
}


@Composable
fun HealthRecordCard(record: HealthRecord) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF9F9F9)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = record.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0066CC)
            )
            Text(
                text = "Date: ${record.date}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = record.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

