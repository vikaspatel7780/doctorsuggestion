package com.myapp.voicehealth.domain.models

import com.myapp.voicehealth.R

data class Doctor(
    val id: Int,
    val name: String,
    val specialty: String,
    val rating: Double,
    val about: String,
    val availableSlots: List<String>,
    val photoResId: Int,
    val location: String = "Lucknow",
    val experience: Int = 7,
    val hospitalName: String = "HealthCare Plus Hospital"
)



val doctorList = listOf(
    Doctor(1, "Dr. Anjali Verma", "Cardiologist", 4.8,
        "Experienced cardiologist with 12 years of practice treating heart conditions.",
        listOf("10:00 AM", "12:30 PM", "3:00 PM"), R.drawable.profile_user,
        "Apollo Hospital, Delhi", 12, "Apollo Hospital"),

    Doctor(2, "Dr. Ramesh Kumar", "Neurologist", 4.7,
        "Specialist in brain and nervous system disorders with a decade of expertise.",
        listOf("11:00 AM", "2:00 PM", "5:00 PM"), R.drawable.profile_user,
        "Fortis Hospital, Bengaluru", 10, "Fortis Hospital"),

    Doctor(3, "Dr. Priya Singh", "Dermatologist", 4.6,
        "Expert in skin treatments, acne, and cosmetic dermatology.",
        listOf("9:30 AM", "1:00 PM", "4:30 PM"), R.drawable.profile_user,
        "SkinCare Clinic, Mumbai", 8, "SkinCare Clinic"),

    Doctor(4, "Dr. Vivek Raj", "Pediatrician", 4.9,
        "Trusted pediatrician caring for children with compassion and skill.",
        listOf("10:00 AM", "1:00 PM", "6:00 PM"), R.drawable.profile_user,
        "Rainbow Hospital, Hyderabad", 11, "Rainbow Hospital"),

    Doctor(5, "Dr. Sneha Mehta", "Gynecologist", 4.7,
        "Women’s health expert with extensive experience in obstetrics and gynecology.",
        listOf("8:00 AM", "12:00 PM", "5:00 PM"), R.drawable.profile_user,
        "Cloudnine, Pune", 9, "Cloudnine Hospital"),

    Doctor(6, "Dr. Arjun Nair", "Orthopedic", 4.5,
        "Bone and joint specialist with focus on sports injuries and arthritis.",
        listOf("9:00 AM", "11:00 AM", "2:00 PM"), R.drawable.profile_user,
        "Max Healthcare, Delhi", 13, "Max Healthcare"),

    Doctor(7, "Dr. Neha Bhatia", "Psychiatrist", 4.8,
        "Mental health expert treating depression, anxiety, and behavioral issues.",
        listOf("11:30 AM", "3:00 PM", "6:30 PM"), R.drawable.profile_user,
        "MindCare Clinic, Chandigarh", 7, "MindCare Clinic"),

    Doctor(8, "Dr. Amit Desai", "ENT Specialist", 4.6,
        "Ear, nose, and throat surgeon with vast ENT experience.",
        listOf("10:00 AM", "1:00 PM", "4:00 PM"), R.drawable.profile_user,
        "Care Hospital, Ahmedabad", 10, "Care Hospital"),

    Doctor(9, "Dr. Jyoti Sinha", "Endocrinologist", 4.7,
        "Expert in diabetes, thyroid, and hormonal disorders.",
        listOf("9:30 AM", "12:30 PM", "5:00 PM"), R.drawable.profile_user,
        "Endo Clinic, Lucknow", 8, "Endo Clinic"),

    Doctor(10, "Dr. Rajat Malhotra", "Dentist", 4.9,
        "Specializes in painless dentistry and cosmetic procedures.",
        listOf("10:30 AM", "2:00 PM", "6:00 PM"), R.drawable.profile_user,
        "Smile Dental Care, Jaipur", 6, "Smile Dental Care"),

    Doctor(11, "Dr. Shweta Kapoor", "Oncologist", 4.8,
        "Cancer specialist with compassionate care and latest treatment methods.",
        listOf("10:00 AM", "1:00 PM", "3:30 PM"), R.drawable.profile_user,
        "Tata Memorial Hospital, Mumbai", 14, "Tata Memorial"),

    Doctor(12, "Dr. Sameer Qureshi", "General Physician", 4.5,
        "Provides general health checkups, minor ailments, and preventive care.",
        listOf("8:30 AM", "11:30 AM", "2:30 PM"), R.drawable.profile_user,
        "City Hospital, Patna", 9, "City Hospital"),

    Doctor(13, "Dr. Meera Nanda", "Nephrologist", 4.7,
        "Kidney specialist with expertise in dialysis and renal care.",
        listOf("9:00 AM", "12:00 PM", "4:00 PM"), R.drawable.profile_user,
        "AIIMS, Delhi", 15, "AIIMS"),

    Doctor(14, "Dr. Ashok Goyal", "Gastroenterologist", 4.6,
        "Expert in stomach, liver, and digestive tract diseases.",
        listOf("10:00 AM", "1:30 PM", "5:00 PM"), R.drawable.profile_user,
        "Manipal Hospital, Bengaluru", 11, "Manipal Hospital"),

    Doctor(15, "Dr. Karuna Pandey", "Rheumatologist", 4.8,
        "Specializes in autoimmune and joint disorders like arthritis and lupus.",
        listOf("9:30 AM", "12:00 PM", "3:30 PM"), R.drawable.profile_user,
        "Healing Touch Clinic, Kolkata", 10, "Healing Touch Clinic"),

    Doctor(16, "Dr. Harshita Roy", "Pulmonologist", 4.6,
        "Lung specialist with focus on asthma, TB, and sleep disorders.",
        listOf("10:00 AM", "1:00 PM", "5:00 PM"), R.drawable.profile_user,
        "LungCare Hospital, Bhopal", 7, "LungCare Hospital"),

    Doctor(17, "Dr. Vinayak Joshi", "Urologist", 4.7,
        "Treats urinary tract issues and prostate conditions.",
        listOf("8:30 AM", "12:30 PM", "4:30 PM"), R.drawable.profile_user,
        "UroPlus Clinic, Nagpur", 12, "UroPlus Clinic"),

    Doctor(18, "Dr. Kavita Rawat", "Ophthalmologist", 4.8,
        "Eye care expert performing surgeries and routine checkups.",
        listOf("9:00 AM", "1:00 PM", "3:00 PM"), R.drawable.profile_user,
        "Vision Eye Center, Surat", 9, "Vision Eye Center"),

    Doctor(19, "Dr. Nikhil Bansal", "Hematologist", 4.9,
        "Specializes in blood disorders, anemia, and leukemia treatments.",
        listOf("10:30 AM", "12:30 PM", "4:30 PM"), R.drawable.profile_user,
        "Medanta, Gurugram", 13, "Medanta"),

    Doctor(20, "Dr. Anuja Thakur", "Pathologist", 4.5,
        "Behind-the-scenes diagnostic expert handling all lab reports.",
        listOf("9:00 AM", "11:00 AM", "2:00 PM"), R.drawable.profile_user,
        "Red Cross Labs, Delhi", 8, "Red Cross Labs")
)



data class Message(val text: String, val isUser: Boolean)
data class HealthRecord(
    val title: String,
    val date: String,
    val description: String
)
