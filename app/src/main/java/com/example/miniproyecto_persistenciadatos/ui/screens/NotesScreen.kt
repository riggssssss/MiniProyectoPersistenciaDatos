package com.example.miniproyecto_persistenciadatos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.miniproyecto_persistenciadatos.data.Note
import com.example.miniproyecto_persistenciadatos.ui.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NoteViewModel,
    onNavigateToAddNote: () -> Unit,
    onOpenDrawer: () -> Unit // Nuevo parámetro para abrir el menú
) {
    val notes by viewModel.notes.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Diario Personal") },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú lateral"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddNote) {
                Icon(Icons.Default.Add, contentDescription = "Agregar nota")
            }
        }
    ) { paddingValues ->
        if (notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No hay notas. ¡Crea tu primera entrada!",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notes) { note ->
                    NoteCard(
                        note = note,
                        onDeleteClick = { viewModel.deleteNote(note) }
                    )
                }
            }
        }
    }
}

// Nota: La función NoteCard y las funciones utilitarias (formatDate, getMoodEmoji)
// permanecen exactamente igual, no hace falta modificarlas.
@Composable
fun NoteCard(
    note: Note,
    onDeleteClick: () -> Unit
) {
    // ... tu código original de NoteCard ...
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun getMoodEmoji(mood: String): String {
    // ... tu código original de getMoodEmoji ...
    return when (mood) {
        "happy" -> "😊 Feliz"
        "sad" -> "😢 Triste"
        "excited" -> "🤩 Emocionado"
        "thoughtful" -> "🤔 Pensativo"
        else -> "😐 Neutral"
    }
}