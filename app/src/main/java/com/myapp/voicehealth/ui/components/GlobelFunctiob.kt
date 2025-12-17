package com.myapp.voicehealth.ui.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.myapp.voicehealth.R
import com.myapp.voicehealth.data.utils.CbcNormalRanges
import com.myapp.voicehealth.domain.models.CBC_FIELDS
import com.myapp.voicehealth.domain.models.CbcImportantData
import com.myapp.voicehealth.domain.models.CbcResult
import com.myapp.voicehealth.domain.models.Doctor
import com.myapp.voicehealth.ui.theme.PrimaryBlue
import com.myapp.voicehealth.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DoctorCard(doctor: Doctor, onBookClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = { onBookClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = doctor.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PrimaryBlue
                )
                Text(
                    text = doctor.specialty,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "📍 ${doctor.location}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(
                    text = "⭐ ${doctor.rating}",
                    fontSize = 13.sp,
                    color = Color(0xFFFFC107)
                )
            }

            Button(
                onClick = onBookClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Book",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
fun VoiceHealthTopBar(
    title: String = "VoiceHealth",
    showUpgradeButton: Boolean = true,
    onMenuClick: () -> Unit,
    onUpgradeClick: () -> Unit = {}
) {
    val statusBarHeight = WindowInsets.statusBars
        .asPaddingValues()
        .calculateTopPadding()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryBlue) // 🔥 fills status bar area + top bar with SAME color
    ) {
        Spacer(
            modifier = Modifier
                .height(statusBarHeight)   // same color as topbar
                .fillMaxWidth()
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = PrimaryBlue,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                if (showUpgradeButton) {
                    Button(
                        onClick = onUpgradeClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB388FF)),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Get Plus +",
                            color = Color.White,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CommonTopBar(
    title: String,
    drawerState: DrawerState? = null,    // ⭐ Make it OPTIONAL
    showDrawer: Boolean = false,
    onBack: (() -> Unit) = {}
) {
    val scope = rememberCoroutineScope()

    val statusBarHeight = WindowInsets.statusBars
        .asPaddingValues()
        .calculateTopPadding()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryBlue)
    ) {

        Spacer(
            modifier = Modifier
                .height(statusBarHeight)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(PrimaryBlue)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // ---------- LEFT SIDE (Back + Title) ----------
            Row(verticalAlignment = Alignment.CenterVertically) {

                // 🔙 SHOW BACK BUTTON ONLY IF PROVIDED
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }

            // ---------- RIGHT SIDE (MENU ICON) ----------
            if (showDrawer && drawerState != null) {
                IconButton(
                    onClick = {
                        scope.launch {
                            if (drawerState.isOpen) drawerState.close()
                            else drawerState.open()
                        }
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}



@Composable
fun CommonTopBar1(
    title: String,
    drawerState: DrawerState,
    scope: CoroutineScope,
    showBack: Boolean = false,
    onBack: (() -> Unit)? = null
) {
    val statusBarHeight = WindowInsets.statusBars
        .asPaddingValues()
        .calculateTopPadding()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryBlue)
    ) {

        // 🔵 Status bar background
        Spacer(
            modifier = Modifier
                .height(statusBarHeight)
                .fillMaxWidth()
        )

        // 🔵 Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(PrimaryBlue)
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 👈 Left Icon - Back or Drawer
            IconButton(
                onClick = {
                    if (showBack) onBack?.invoke()
                    else scope.launch { drawerState.open() }     // ⭐ OPEN DRAWER
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (showBack) Icons.Default.ArrowBack else Icons.Default.Menu,
                    contentDescription = if (showBack) "Back" else "Menu",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // 🔵 Title
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun FeatureTile(title: String, iconRes: Int = R.drawable.mic, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F9FF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                tint = PrimaryBlue
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center
            )
        }
    }
}
@Composable
fun BottomInputBar(
    onSend: (String) -> Unit = {},
    onMicClick: (() -> Unit) = {}
) {
    var inputText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()   // ⭐ Lifts input bar above keyboard
    ) {
        Surface(
            tonalElevation = 4.dp,
            shadowElevation = 6.dp,
            color = Color.White
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Input Field Box
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = Color(0xFFF0F0F0),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    BasicTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
                        maxLines = 3,
                        decorationBox = { innerTextField ->
                            if (inputText.isEmpty()) {
                                Text(
                                    "Ask anything…",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Mic Button (Rounded Icon)
                IconButton(
                    onClick = { onMicClick?.invoke() },
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = Color(0xFFE8F0FE),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "Mic",
                        tint = Color(0xFF0A84FF),
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                // Send Button
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSend(inputText)
                            inputText = ""
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = Color(0xFF0A84FF),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

fun extractTextFromImage(context: Context, uri: Uri, onResult: (String) -> Unit) {
    val image = InputImage.fromFilePath(context, uri)
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            onResult(visionText.text)
        }
        .addOnFailureListener {
            onResult("Failed to extract text.")
        }
}
fun mockSymptomAnalysis(selectedReportType: String,text: String): String {
    return if ("fever" in text.lowercase()) {
        "Possible symptoms: Fever, Infection"
    } else {
        "No clear symptoms detected. Please consult a doctor."
    }
}
@Composable
fun DrawerContentChat(
    onNewChat: () -> Unit,
    onHistory: () -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(280.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, top = 40.dp)
    ) {
        Text("Menu", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(24.dp))

        Text(
            "New Chat",
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { onNewChat() }
        )

        Text(
            "Chat History",
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { onHistory() }
        )

        Spacer(Modifier.weight(1f))

        Text(
            "Close",
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { onClose() },
            color = Color.Red
        )
    }
}
fun extractNumber(line: String): String? {
    return Regex("""\b\d+(\.\d+)?\b""").find(line)?.value
}

fun extractCbcFromOcr(ocrText: String): CbcResult {

    val lines = ocrText
        .lowercase()
        .lines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    val extracted = mutableMapOf<String, String>()

    for (i in lines.indices) {
        val line = lines[i]

        CBC_FIELDS.forEach { (key, keywords) ->
            if (keywords.any { line.contains(it) }) {

                // Scan next 4 lines for numeric value
                for (j in 1..4) {
                    if (i + j < lines.size) {
                        extractNumber(lines[i + j])?.let {
                            extracted[key] = it
                            return@forEach
                        }
                    }
                }
            }
        }
    }

    return CbcResult(
        hemoglobin = extracted["hemoglobin"],
        rbc = extracted["rbc"],
        hematocrit = extracted["hematocrit"],
        mcv = extracted["mcv"],
        mch = extracted["mch"],
        mchc = extracted["mchc"],
        rdwCv = extracted["rdwcv"],
        rdwSd = extracted["rdwsd"],
        platelets = extracted["platelets"],
        mpv = extracted["mpv"],
        wbc = extracted["wbc"],
        neutrophilsPct = extracted["neutrophils_pct"],
        lymphocytesPct = extracted["lymphocytes_pct"],
        monocytesPct = extracted["monocytes_pct"],
        eosinophilsPct = extracted["eosinophils_pct"],
        basophilsPct = extracted["basophils_pct"]
    )
}


fun formatCbcSummary(cbc: CbcResult): String {
    return """
Hemoglobin: ${cbc.hemoglobin ?: "Not found"}
WBC Count: ${cbc.wbc ?: "Not found"}
Platelet Count: ${cbc.platelets ?: "Not found"}
RBC Count: ${cbc.rbc ?: "Not found"}
Hematocrit (PCV): ${cbc.hematocrit ?: "Not found"}
MCV: ${cbc.mcv ?: "Not found"}
""".trimIndent()
}
fun buildCbcGeminiPrompt(
    cbcData: CbcResult
): String {

    val structuredSummary = formatCbcSummary(cbcData)

    return """
You are a medical assistant.

Below is a structured summary extracted from a Complete Blood Count (CBC) report.
Use this summary as the primary source.
Use the raw report text only for additional context.

Your tasks:
- Explain each value in simple language
- Highlight values that are high or low
- Do NOT provide a diagnosis
- Add a safety disclaimer

Structured CBC Summary:
$structuredSummary

Raw CBC Report Text:
""".trimIndent()
}
