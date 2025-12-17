package com.myapp.voicehealth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.myapp.voicehealth.data.local.AppState
import com.myapp.voicehealth.domain.sealedClass.bottomNavItems
import com.myapp.voicehealth.navigation.BottomNavigationBar
import com.myapp.voicehealth.navigation.NavGraph

@Composable
fun VoiceHealthApp() {

    val navController = rememberNavController()
    val appState = remember { AppState() }

    Scaffold(
        contentWindowInsets = WindowInsets(0), // ✅ disable auto padding
        bottomBar = {
            if (appState.showBottomBar.value) {
                BottomNavigationBar(navController, bottomNavItems)
            }
        }
    ) {
        NavGraph(
            navController = navController,
            appState = appState
        )
    }
}

