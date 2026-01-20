package com.example.miniproyecto_persistenciadatos.data

data class UserPreferences(
    val fontSize: Int = 16,
    val isDarkTheme: Boolean = false,
    val defaultMood: String = "neutral"
)
