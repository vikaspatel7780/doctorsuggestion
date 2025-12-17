# DoctorSuggestion 🩺  
AI-Powered Health Chat & Medical Report Assistant

## Overview
DoctorSuggestion is an Android application that leverages **Google Gemini API** to assist users with health-related questions through AI-powered chat and medical report scanning. The app is designed to provide **safe, non-diagnostic health guidance** and help users digitally manage their medical reports.

The goal of DoctorSuggestion is to improve health awareness and accessibility using conversational AI while maintaining privacy and clarity.

---

## Key Features

### 🤖 AI Health Chat (Gemini API)
- Chat-based interaction for health-related questions
- Powered by Google Gemini API
- Context-aware, short, and clear responses
- No disease diagnosis or medicine prescription

### 📄 Medical Report Scanning
- Scan and upload medical reports
- Store reports locally for future reference
- View reports inside the app

### 🧠 Smart Assistance
- Understands common health symptoms
- Provides general wellness suggestions
- Encourages professional medical consultation when needed

### 🔐 User Authentication
- Login & Signup flow
- Secure token-based session handling

---

## Tech Stack

### Android
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM
- **Navigation:** Navigation Compose

### AI & APIs
- **Google Gemini API** (for AI chat)
- Retrofit for API communication

### Storage
- Room Database (medical reports)
- SharedPreferences / DataStore

### Tools
- Android Studio
- Git & GitHub
- Postman

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
