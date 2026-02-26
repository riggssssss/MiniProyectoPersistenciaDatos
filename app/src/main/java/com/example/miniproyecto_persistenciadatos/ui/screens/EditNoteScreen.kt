package com.example.miniproyecto_persistenciadatos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.miniproyecto_persistenciadatos.ui.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    viewModel: NoteViewModel,
    noteId: Int,
    onNavigateBack: () -> Unit
) {
    // Estado para los campos del formulario
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf("neutral") }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar la nota existente al abrir la pantalla
    LaunchedEffect(noteId) {
        val note = viewModel.getNoteById(noteId)
        if (note != null) {
            title = note.title
            content = note.content
            selectedMood = note.mood
        }
        isLoading = false
    }

    val moodOptions = listOf(
        "neutral" to "😐 Neutral",
        "happy" to "😊 Feliz",
        "sad" to "😢 Triste",
        "excited" to "🤩 Emocionado",
        "thoughtful" to "🤔 Pensativo"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Entrada") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Contenido") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10
                )

                Text(
                    text = "Estado de ánimo:",
                    style = MaterialTheme.typography.titleMedium
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    moodOptions.forEach { (value, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (selectedMood == value),
                                    onClick = { selectedMood = value },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (selectedMood == value),
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        viewModel.updateNote(noteId, title, content, selectedMood)
                        onNavigateBack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank() && content.isNotBlank()
                ) {
                    Text("Guardar Cambios")
                }
            }
        }
    }
}
