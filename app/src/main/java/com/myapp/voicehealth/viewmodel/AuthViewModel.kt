package com.myapp.voicehealth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.myapp.voicehealth.core.network.ApiClient
import com.myapp.voicehealth.domain.models.LoginRequest
import com.myapp.voicehealth.domain.models.LoginResponse
import com.myapp.voicehealth.domain.models.RegisterRequest
import com.myapp.voicehealth.domain.models.RegisterResponse
import com.myapp.voicehealth.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse

    private val _loggedInUser = MutableStateFlow<User?>(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken: StateFlow<String?> = _accessToken

    private val _loginStateMessage = MutableStateFlow("")
    val loginStateMessage: StateFlow<String> = _loginStateMessage

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> = _isLoggingIn
    private val _registerResponse = MutableStateFlow<RegisterResponse?>(null)
    val registerResponse: StateFlow<RegisterResponse?> = _registerResponse
    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun login(
        email: String,
        password: String,
        onLogin: (LoginResponse?) -> Unit
    ) {
        viewModelScope.launch {
            Log.d("AuthViewModel", "Login started with email: $email")
            _isLoggingIn.value = true

            try {
                val response = ApiClient.authService.login(LoginRequest(email, password))
                Log.d("AuthViewModel", "API response code: ${response}")

                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("AuthViewModel", "Response body: $result")

                    if (result?.success == true) {
                        _loginResponse.value = result
                        _accessToken.value = result.data?.accessToken
                        _loggedInUser.value = result.data?.user
                        _loginStateMessage.value = "Login successful"

                        Log.d("AuthViewModel", "Login successful. Token: ${result}")
                        onLogin(result) // ✅ invoke callback with login result
                    } else {
                        _loginStateMessage.value = "Login failed: ${result?.message}"
                        Log.e("AuthViewModel", "Login failed: ${result?.message}")
                        onLogin(null) // send null for failure
                    }
                } else {
                    _loginStateMessage.value = "Error: ${response.code()}"
                    Log.e("AuthViewModel", "HTTP error: ${response.code()}")
                    onLogin(null)
                }
            } catch (e: Exception) {
                _loginStateMessage.value = "Network error: ${e.localizedMessage}"
                Log.e("AuthViewModel", "Login exception: ${e.localizedMessage}", e)
                onLogin(null)
            } finally {
                _isLoggingIn.value = false
                Log.d("AuthViewModel", "Login process finished")
            }
        }
    }
    fun firebaseLogin(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.localizedMessage)
            }
    }

    fun firebaseRegister(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.localizedMessage)
            }
    }


    fun firebaseGoogleLogin(idToken: String, onResult: (Boolean, String?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                onResult(true, user?.uid)
            }
            .addOnFailureListener { e ->
                onResult(false, e.localizedMessage)
            }
    }

    fun registerUser(
        request: RegisterRequest,
        onRegister: (RegisterResponse?) -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                ApiClient.authService.registerUser(request)
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val body = response.body()
                    _registerResponse.value = body

                    if (body?.success == true) {
                        _errorMessage.value = ""
                        onRegister(body)
                    } else {
                        _errorMessage.value = body?.message ?: "Unknown error"
                        onRegister(body)
                    }
                } else {
                    _errorMessage.value = "Server error: ${response.code()}"
                    onRegister(null)
                }
            }.onFailure { exception ->
                _errorMessage.value = "Network error: ${exception.localizedMessage ?: "Unknown error"}"
                onRegister(null)
            }
        }
    }

}
