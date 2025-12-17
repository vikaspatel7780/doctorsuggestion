package com.myapp.voicehealth.domain.models

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("contact_number") val contactNumber: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("address_detail") val addressDetail: String
)

data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: RegisterData? = null
)

data class RegisterData(
    @SerializedName("_id") val id: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("email") val email: String,
    @SerializedName("contact_number") val contactNumber: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("role") val role: String,
    @SerializedName("is_email_verified") val isEmailVerified: Boolean
)
