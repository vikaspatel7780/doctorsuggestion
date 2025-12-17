package com.myapp.voicehealth.domain.models

data class CbcImportantData(
    val hemoglobin: String?,
    val wbc: String?,
    val platelets: String?,
    val rbc: String?,
    val hematocrit: String?,
    val mcv: String?
)

data class CbcResult(
    val hemoglobin: String? = null,
    val rbc: String? = null,
    val hematocrit: String? = null,
    val mcv: String? = null,
    val mch: String? = null,
    val mchc: String? = null,
    val rdwCv: String? = null,
    val rdwSd: String? = null,
    val platelets: String? = null,
    val mpv: String? = null,
    val wbc: String? = null,
    val neutrophilsPct: String? = null,
    val lymphocytesPct: String? = null,
    val monocytesPct: String? = null,
    val eosinophilsPct: String? = null,
    val basophilsPct: String? = null
)
val CBC_FIELDS = mapOf(
    "hemoglobin" to listOf("hemoglobin", "hb"),
    "rbc" to listOf("rbc count", "total rbc"),
    "hematocrit" to listOf("hematocrit", "hct", "pcv"),
    "mcv" to listOf("mcv"),
    "mch" to listOf("mch"),
    "mchc" to listOf("mchc"),
    "rdwcv" to listOf("rdw-cv"),
    "rdwsd" to listOf("rdw-sd"),
    "platelets" to listOf("platelet count", "platelets"),
    "mpv" to listOf("mpv"),
    "wbc" to listOf("total count (wbc)", "wbc"),
    "neutrophils_pct" to listOf("neutrophils (%)"),
    "lymphocytes_pct" to listOf("lymphocytes (%)"),
    "monocytes_pct" to listOf("monocytes (%)"),
    "eosinophils_pct" to listOf("eosinophils (%)"),
    "basophils_pct" to listOf("basophils (%)")
)
