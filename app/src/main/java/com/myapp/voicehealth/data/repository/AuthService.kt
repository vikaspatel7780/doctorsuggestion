package com.myapp.voicehealth.data.repository



import com.myapp.voicehealth.domain.models.LoginRequest
import com.myapp.voicehealth.domain.models.LoginResponse
import com.myapp.voicehealth.domain.models.RegisterRequest
import com.myapp.voicehealth.domain.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    @POST("auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}
