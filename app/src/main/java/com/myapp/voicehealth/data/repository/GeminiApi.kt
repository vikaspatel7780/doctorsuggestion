package com.myapp.voicehealth.data.repository

import com.myapp.voicehealth.domain.models.GeminiRequest
import com.myapp.voicehealth.domain.models.GeminiResponse
import retrofit2.http.*

interface GeminiApi {

    @POST("v1beta/models/gemini-2.5-flash:generateContent")
    suspend fun generateText(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}
