package com.myapp.voicehealth.data.repository

import com.myapp.voicehealth.core.network.GeminiClient
import com.myapp.voicehealth.domain.models.ContentRequest
import com.myapp.voicehealth.domain.models.GeminiRequest
import com.myapp.voicehealth.domain.models.PartRequest
import retrofit2.HttpException
import java.io.IOException

class QuotaExceededException(message: String) : Exception(message)

object GeminiService {

    suspend fun ask(prompt: String, apiKey: String): String {

        try {
            // 🟦 SYSTEM MESSAGE — uses "model" role (Gemini requirement)
            val systemPrompt = ContentRequest(
                role = "model",
                parts = listOf(
                    PartRequest(
                        text =
                            """
You are VoiceHealth, a personal AI health assistant.

RULES:
- Give safe, simple wellness guidance directly related to the user’s question.
- No medical diagnosis. No medicine prescriptions.
- If symptoms sound serious → say: “Please consult a certified doctor.”
- Respond briefly, clearly, and always suitable for mobile reading.
- If asked your name → reply: “VoiceHealth.”
- Founders:
  1. Vikas Patel — Founder & CEO
  2. Jeetendra Kumar — CTO
- No extra info unless user explicitly asks.
- Stay friendly, supportive, and concise.

Follow these rules in every message.
"""
                    )
                )
            )

            // 🟩 USER MESSAGE
            val userPrompt = ContentRequest(
                role = "user",
                parts = listOf(PartRequest(text = prompt))
            )

            // 🟪 Final Request Body
            val request = GeminiRequest(
                contents = listOf(systemPrompt, userPrompt)
            )

            // 🟦 API CALL
            val response = GeminiClient.api.generateText(apiKey, request)

            return response.candidates
                ?.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text ?: "No response available."

        } catch (e: HttpException) {

            // ❗ Daily FREE limit exceeded (20 per day per model)
            if (e.code() == 429) {
                throw QuotaExceededException("FREE TIER LIMIT REACHED")
            }

            // ❗ Invalid prompt or request structure
            if (e.code() == 400) {
                return "Invalid request. Try rephrasing your question."
            }

            return "Server error: ${e.message()}"
        }
        catch (e: IOException) {
            return "Network issue. Please check your internet."
        }
        catch (e: Exception) {
            return "Something went wrong. Try again."
        }
    }
}
