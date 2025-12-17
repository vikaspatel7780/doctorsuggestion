package com.myapp.voicehealth.domain.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UserData?
)

data class UserData(
    @SerializedName("accessToken") val accessToken: String?,
    @SerializedName("user") val user: User?
)

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("contact_number") val contactNumber: String?,
    @SerializedName("country_code") val countryCode: String?,
    @SerializedName("role") val role: String,
    @SerializedName("is_email_verified") val isEmailVerified: Boolean
)

