# 🩺 DoctorSuggestion  
### AI-Powered Health Assistant, Medical Report Analysis & Doctor Recommendation App

<p align="center">
  <img src="https://img.shields.io/badge/Android-Kotlin-3DDC84?style=for-the-badge&logo=android" />
  <img src="https://img.shields.io/badge/Jetpack%20Compose-UI-4285F4?style=for-the-badge&logo=jetpackcompose" />
  <img src="https://img.shields.io/badge/Google-Gemini%20API-FF6F00?style=for-the-badge&logo=google" />
  <img src="https://img.shields.io/badge/MVVM-Architecture-blue?style=for-the-badge" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Status-Active%20Development-yellow?style=flat-square" />
  <img src="https://img.shields.io/badge/AI-Health%20Assistant-green?style=flat-square" />
</p>

---

## 📌 Overview

**DoctorSuggestion** is an Android application that leverages **Artificial Intelligence powered by the Google Gemini API** to assist users with health-related concerns.

The app enables users to:
📄 Upload and analyze medical reports
🤖 Chat with AI for general health guidance
🏥 Get nearby doctor suggestions when potential risks are detected

The goal is to promote **early awareness**, **safe health guidance**, and **timely medical action** — while **never replacing professional medical advice**.

---

## ✨ Key Features

### 🤖 AI Health Chat
Chat with AI about general health concerns
Powered by **Google Gemini API**
Provides informational (non-diagnostic) health guidance
Detects potentially critical symptoms
Suggests consulting nearby doctors when required

---

### 📄 Medical Report Analysis (Primary Flow)
Upload medical reports (lab results, test summaries)
AI processes reports using **Google Gemini API**
Extracts key health indicators and observations
Classifies reports as:
  - 🟢 **Normal** — No immediate risk indicators
  - 🔴 **Critical** — Potential health concerns identified
🧠 **Clinical Assistance (In Development)**  
  - Critical reports trigger **context-aware doctor recommendations**

---

### 🏥 Doctor Recommendation
Suggests nearby doctors for critical cases
Displays basic doctor information:
  - Name
  - Specialization
  - Location
Allows users to choose a doctor for follow-up

---

### 📅 Appointment Booking
Book appointments directly from the app
Store appointment details locally for easy access

---

## 🔄 Application Workflow

### 🧪 Medical Report–First Flow
1. User uploads a medical report  
2. AI analyzes the report  
3. Condition classified as **Normal** or **Critical**  
4. Normal → General health guidance shown  
5. Critical → Nearby doctors suggested  
6. User books an appointment  

---

### 💬 AI Health Chat Flow
1. User chats with AI about health issues  
2. AI provides general guidance  
3. Critical symptoms detected → Doctor suggestions shown  

---

## 🛠️ Tech Stack

### 📱 Android
<p>
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white"/>
  <img src="https://img.shields.io/badge/MVVM-Architecture-blue?style=for-the-badge"/>
</p>

### 🤖 AI & Networking
<p>
  <img src="https://img.shields.io/badge/Google%20Gemini-AI-orange?style=for-the-badge&logo=google"/>
  <img src="https://img.shields.io/badge/Retrofit-Networking-green?style=for-the-badge"/>
</p>

### 💾 Storage
<p>
  <img src="https://img.shields.io/badge/Room-Database-red?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/DataStore-Preferences-blue?style=for-the-badge"/>
</p>

---

## Project Structure

```text
com.myapp.doctorsuggestion
│
├── data
│   ├── repository
│   ├── network
│   └── roomdb
│
├── domain
│   └── models
│
├── ui
│   ├── screens
│   ├── components
│   └── theme
│
├── navigation
│
└── core
    └── utils
