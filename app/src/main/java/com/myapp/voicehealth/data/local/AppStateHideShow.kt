package com.myapp.voicehealth.data.local

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


class AppState {
    var showBottomBar = mutableStateOf(true)
        private set

    fun hideBottomBar() { showBottomBar.value = false }
    fun showBottomBar() { showBottomBar.value = true }
}
