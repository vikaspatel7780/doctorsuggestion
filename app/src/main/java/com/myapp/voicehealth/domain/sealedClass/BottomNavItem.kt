package com.myapp.voicehealth.domain.sealedClass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home_screen", "Home", Icons.Default.Home)
    object Doctors : BottomNavItem("nearby_doctors_screen", "Doctors", Icons.Default.Person)
    object Chat : BottomNavItem("ask_ai_screen", "Chat", Icons.Default.Person)
    object Profile : BottomNavItem("profile_screen", "Profile", Icons.Default.Person)
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Doctors,
    BottomNavItem.Chat,
    BottomNavItem.Profile
)
