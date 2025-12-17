package com.myapp.voicehealth.navigation


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.myapp.voicehealth.data.local.AppState
import com.myapp.voicehealth.ui.screens.chatScreen.AskAIScreen
import com.myapp.voicehealth.ui.screens.DoctorDetailsScreen
import com.myapp.voicehealth.ui.screens.DoctorSuggestionScreen
import com.myapp.voicehealth.ui.screens.ForgotPasswordScreen
import com.myapp.voicehealth.ui.screens.HealthHistoryScreen
import com.myapp.voicehealth.ui.screens.HomeScreen
import com.myapp.voicehealth.ui.screens.authScreens.LoginScreen
import com.myapp.voicehealth.ui.screens.NearbyDoctorsScreen
import com.myapp.voicehealth.ui.screens.OtpVerificationScreen
import com.myapp.voicehealth.ui.screens.ProfileScreen
import com.myapp.voicehealth.ui.screens.ScanReportScreen
import com.myapp.voicehealth.ui.screens.authScreens.SignUpScreen
import com.myapp.voicehealth.ui.screens.SplashScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    appState: AppState,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "splash_screen",
        enterTransition = {
            slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -300 }) + fadeOut()
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
        }
    ){
        // ---------- Screens WITHOUT bottom bar ----------
        composable("splash_screen") {
            appState.hideBottomBar()
            SplashScreen(navController)
        }

        composable("login_screen") {
            appState.hideBottomBar()
            LoginScreen(navController)
        }

        composable("register_screen") {
            appState.hideBottomBar()
            SignUpScreen(navController)
        }

        composable("forgot_password_screen") {
            appState.hideBottomBar()
            ForgotPasswordScreen(navController)
        }

        composable("otp_verification_screen") {
            appState.hideBottomBar()
            OtpVerificationScreen(navController)
        }

        composable("scan_report_screen") {
            appState.hideBottomBar()
            ScanReportScreen(navController)
        }

        composable("ask_ai_screen") {
            appState.hideBottomBar()
            AskAIScreen(navController)
        }

        composable("health_history_screen") {
            appState.hideBottomBar()
            HealthHistoryScreen(navController)
        }

        composable("doctor_suggestion") {
            appState.hideBottomBar()
            DoctorSuggestionScreen(navController)
        }

        composable(
            route = "doctor_details/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.IntType })
        ) { backStack ->
            appState.hideBottomBar()
            val doctorId = backStack.arguments?.getInt("doctorId") ?: -1
            DoctorDetailsScreen(navController, doctorId)
        }

        // ----------- Chat Screen (NO bottom bar) ----------

        // ---------- Screens WITH bottom bar ----------
        composable("home_screen") {
            appState.showBottomBar()
            HomeScreen(navController)
        }

        composable("nearby_doctors_screen") {
            appState.showBottomBar()
            NearbyDoctorsScreen(navController)
        }

        composable("profile_screen") {
            appState.showBottomBar()
            ProfileScreen(navController)
        }
    }
}



