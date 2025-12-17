package com.myapp.voicehealth.data.utils

object CbcNormalRanges {

    fun getStatus(name: String, value: Float): String {
        return when (name) {
            "Hemoglobin" ->
                if (value < 12) "Low" else if (value > 17) "High" else "Normal"

            "RBC Count" ->
                if (value < 4.0) "Low" else if (value > 6.0) "High" else "Normal"

            "WBC Count" ->
                if (value < 4000) "Low" else if (value > 11000) "High" else "Normal"

            "Platelet Count" ->
                if (value < 150000) "Low" else if (value > 450000) "High" else "Normal"

            "Hematocrit" ->
                if (value < 36) "Low" else if (value > 50) "High" else "Normal"

            "MCV" ->
                if (value < 80) "Low" else if (value > 100) "High" else "Normal"

            "MCH" ->
                if (value < 27) "Low" else if (value > 33) "High" else "Normal"

            "MCHC" ->
                if (value < 32) "Low" else if (value > 36) "High" else "Normal"

            else -> "Unknown"
        }
    }
}
