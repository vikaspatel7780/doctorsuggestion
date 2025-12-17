package com.myapp.voicehealth.ui.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.myapp.voicehealth.ui.components.CommonTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyDoctorsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CommonTopBar(
                title = "health Record",
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("View your saved health history.")
            // List reports, symptoms, or doctor visits here
        }
    }
}
