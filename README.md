# DoctorSuggestion 🩺  
AI-Powered Health Chat, Medical Report Analysis & Doctor Recommendation App

## Overview
DoctorSuggestion is an Android application that uses **Artificial Intelligence powered by the Google Gemini API** to assist users with health-related concerns. The app enables users to **upload medical reports**, **chat with AI about health problems**, and receive **nearby doctor suggestions when a condition is identified as critical**.

The application focuses on **safe health guidance**, **early awareness**, and **timely medical action** without providing medical diagnosis or prescriptions.

---

## Key Features

### 🤖 AI Health Chat
- Chat with AI about health problems
- Powered by Google Gemini API
- Provides general health suggestions
- Detects critical symptoms and suggests doctors

### 📄 Medical Report Analysis (Primary Flow)
- Upload medical reports
- AI analyzes reports using Gemini API
- Classifies reports as **Normal** or **Critical**
- Critical reports trigger doctor suggestions

### 🏥 Doctor Recommendation
- Suggests nearby doctors for critical conditions
- Displays doctor list with basic details
- Allows users to select a doctor

### 📅 Appointment Booking
- Book appointments directly from the app
- Store appointment details locally

---

## Application Workflow

### Medical Report–First Flow
1. User uploads a medical report  
2. AI analyzes the report  
3. Condition is classified as Normal or Critical  
4. Normal → General guidance shown  
5. Critical → Nearby doctors suggested  
6. User books an appointment  

### AI Chat Flow
1. User chats with AI about health issues  
2. AI provides general suggestions  
3. If symptoms are critical → Doctor list is shown  

---

## Tech Stack

### Android
- Kotlin
- Jetpack Compose
- Navigation Compose
- MVVM Architecture

### AI & APIs
- Google Gemini API
- Retrofit for network communication

### Storage
- Room Database
- DataStore / SharedPreferences

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
