package com.myapp.voicehealth.ui.screens.authScreens



import android.util.Log
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.myapp.voicehealth.R
import com.myapp.voicehealth.core.storage.UserPreferences
import com.myapp.voicehealth.domain.PopupType
import com.myapp.voicehealth.domain.models.LoginResponse
import com.myapp.voicehealth.ui.components.StatusPopup
import com.myapp.voicehealth.viewmodel.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    // States for fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var errorOrSuccessTitle by remember { mutableStateOf("Alert") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val viewModel: AuthViewModel = viewModel()
    val isLoggingIn by viewModel.isLoggingIn.collectAsState()
    fun handleLoginResponse(loginResponse: LoginResponse){
        Log.d("loginResponse","login response $loginResponse")
        if(loginResponse.success && loginResponse.data?.user?.isEmailVerified == true){
            val token = loginResponse.data.accessToken ?: ""
            val userPrefs = UserPreferences(context)
            CoroutineScope(Dispatchers.IO).launch {
                userPrefs.saveUserLogin(token)
            }
            navController.navigate("home_screen") {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }else{
            navController.navigate("otp_verification_screen")
        }
    }

    // Gradient background
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF74ebd5), Color(0xFFACB6E5))
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // App Logo or Icon
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "VoiceHealth Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // App Name
            Text(
                text = "VoiceHealth",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Form card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome Back",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3F51B5)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Home
                            else Icons.Filled.Email

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(id = if (passwordVisible) R.drawable.visible else R.drawable.visibility),
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    modifier = Modifier.size(24.dp) // 👈 Set icon size here
                                )
                            }

                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Forgot Password?",
                        fontSize = 14.sp,
                        color = Color(0xFF3F51B5),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { /* static only */
                            navController.navigate("forgot_password_screen")
                            }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            when {
                                email.isBlank() -> {
                                    dialogMessage = "Email is required"
                                    showDialog = true
                                }
                                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                    dialogMessage = "Enter a valid email address"
                                    showDialog = true
                                }
                                password.isBlank() -> {
                                    dialogMessage = "Password is required"
                                    showDialog = true
                                }
                                password.length < 6 -> {
                                    dialogMessage = "Password must be at least 6 characters"
                                    showDialog = true
                                }
                                else -> {
                                    viewModel.firebaseLogin(
                                        email.trim(),
                                        password.trim()
                                    ) { success, error ->

                                        if (success) {
                                            val prefs = UserPreferences(context)
                                            CoroutineScope(Dispatchers.IO).launch {
                                                prefs.saveUserLogin("firebase_logged_in")
                                            }
//                                            handleLoginResponse()  // Navigate to home
                                            navController.navigate("home_screen") {
                                                popUpTo(0) { inclusive = true }
                                                launchSingleTop = true
                                            }
                                        } else {
                                            showDialog = true
                                            dialogMessage = "Login Failed"
                                            errorOrSuccessTitle = "Error"
                                        }
                                    }


                                }
                            }
                        },
                        enabled = !isLoggingIn, // disables button during API call
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = if (isLoggingIn) "Logging in..." else "Login", fontSize = 18.sp)
                    }



                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Don't have an account? Sign Up",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable { /* static only */
                        navController.navigate("register_screen")
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "OR",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { /* Google Sign-In - static */ },
                        shape = RoundedCornerShape(12.dp),
                        border = ButtonDefaults.outlinedButtonBorder,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.google_logo),
                            contentDescription = "Google Logo",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Login with Google", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
    if (showDialog) {
            StatusPopup(
                type = PopupType.ERROR,
                title = errorOrSuccessTitle,
                subtitle = dialogMessage,
                onOk = { showDialog = false },
                onDismiss = { showDialog = false }
            )
        }

}

