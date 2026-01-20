package com.example.miniproyecto_persistenciadatos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: Long,
    val mood: String // "neutral", "happy", "sad", "excited", "thoughtful"
)
