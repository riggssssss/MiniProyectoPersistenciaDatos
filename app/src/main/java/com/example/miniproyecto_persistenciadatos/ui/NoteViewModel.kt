package com.example.miniproyecto_persistenciadatos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniproyecto_persistenciadatos.data.Note
import com.example.miniproyecto_persistenciadatos.data.NoteRepository
import com.example.miniproyecto_persistenciadatos.data.UserPreferences
import com.example.miniproyecto_persistenciadatos.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> = repository.allNotes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val userPreferences: StateFlow<UserPreferences> = preferencesRepository.userPreferencesFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserPreferences()
    )

    fun insertNote(title: String, content: String, mood: String) {
        viewModelScope.launch {
            val note = Note(
                title = title,
                content = content,
                date = System.currentTimeMillis(),
                mood = mood
            )
            repository.insertNote(note)
        }
    }

    suspend fun getNoteById(noteId: Int): Note? {
        return repository.getNoteById(noteId)
    }

    fun updateNote(noteId: Int, title: String, content: String, mood: String) {
        viewModelScope.launch {
            val existingNote = repository.getNoteById(noteId)
            if (existingNote != null) {
                val updatedNote = existingNote.copy(
                    title = title,
                    content = content,
                    mood = mood
                )
                repository.updateNote(updatedNote)
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun updateFontSize(fontSize: Int) {
        viewModelScope.launch {
            preferencesRepository.updateFontSize(fontSize)
        }
    }

    fun updateDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateDarkTheme(isDarkTheme)
        }
    }
}
