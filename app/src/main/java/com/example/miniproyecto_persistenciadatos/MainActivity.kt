package com.example.miniproyecto_persistenciadatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.miniproyecto_persistenciadatos.data.NoteDatabase
import com.example.miniproyecto_persistenciadatos.data.NoteRepository
import com.example.miniproyecto_persistenciadatos.ui.theme.MiniProyectoPersistenciaDatosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = NoteDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(database.noteDao())

        setContent {
            MiniProyectoPersistenciaDatosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiaryApp(repository)
                }
            }
        }
    }
}