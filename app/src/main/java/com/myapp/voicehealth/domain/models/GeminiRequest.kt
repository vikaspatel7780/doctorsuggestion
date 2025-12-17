package com.myapp.voicehealth.domain.models

data class GeminiRequest(
    val contents: List<ContentRequest>
)

data class ContentRequest(
    val role: String = "user",
    val parts: List<PartRequest>
)

data class PartRequest(
    val text: String? = null
)
