package com.myapp.voicehealth.ui.screens.authScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.myapp.voicehealth.domain.PopupType
import com.myapp.voicehealth.domain.models.RegisterRequest
import com.myapp.voicehealth.domain.models.RegisterResponse
import com.myapp.voicehealth.ui.components.AlertPopup
import com.myapp.voicehealth.ui.components.AlertType
import com.myapp.voicehealth.ui.components.StatusPopup
import com.myapp.voicehealth.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var countryCode by remember { mutableStateOf("+91") }
    var address by remember { mutableStateOf("") }
    var showAlert by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val authViewModel: AuthViewModel = viewModel()

    // Validation States
    var errorMessage by remember { mutableStateOf("") }
    var errorOrSuccessTitle by remember { mutableStateOf("Alert") }
    fun isFormValid(): Boolean {
        return when {
            firstName.length < 2 -> {
                errorMessage = "First name must be at least 2 characters"
                showAlert = true
                false
            }
            lastName.length < 2 -> {
                errorMessage = "Last name must be at least 2 characters"
                showAlert = true
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                errorMessage = "Enter a valid email"
                showAlert = true
                false
            }
            contactNumber.length != 10 -> {
                errorMessage = "Enter a valid 10-digit phone number"
                showAlert = true
                false
            }
            password.length < 6 -> {
                errorMessage = "Password must be at least 6 characters"
                showAlert = true
                false
            }
            confirmPassword != password -> {
                errorMessage = "Passwords do not match"
                showAlert = true
                false
            }
            address.length < 5 -> {
                errorMessage = "Please enter a valid address"
                showAlert = true
                false
            }
            else -> {
                errorMessage = ""
                showAlert = true
                true
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(90.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name *") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = countryCode,
                        onValueChange = { countryCode = it },
                        label = { Text("Code") },
                        modifier = Modifier.width(80.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    OutlinedTextField(
                        value = contactNumber,
                        onValueChange = { contactNumber = it },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            Icon(
                                painter = painterResource(id = if (passwordVisible) R.drawable.visible else R.drawable.visibility),
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        }) {
                            Icon(
                                painter = painterResource(id = if (confirmPasswordVisible) R.drawable.visible else R.drawable.visibility),
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )



                Button(
                    onClick = {
                        if (isFormValid()) {
                            val request = RegisterRequest(
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = password,
                                contactNumber = contactNumber,
                                countryCode = countryCode,
                                addressDetail = address
                            )
                            authViewModel.firebaseRegister(
                                email.trim(),
                                password.trim()
                            ) { success, error ->

                                if (success) {
                                    navController.navigate("home_screen") {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }// Navigate or show success popup
                                } else {
                                    showAlert = true
                                    errorMessage = "Register Failed"
                                    errorOrSuccessTitle = "error"
                                }
                            }

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sign Up", fontSize = 18.sp)
                }

                Text(
                    text = "Already have an account? Login",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            navController.navigate("login_screen")
                        }
                        .padding(top = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
    if (showAlert) {
        StatusPopup(
            type = PopupType.ERROR,
            title = errorOrSuccessTitle,
            subtitle = errorMessage,
            onOk = { showAlert = false },
            onDismiss = { showAlert = false }
        )
    }

}


