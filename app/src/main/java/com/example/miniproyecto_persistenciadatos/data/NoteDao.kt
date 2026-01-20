package com.example.miniproyecto_persistenciadatos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAllNotes(): Flow<List<Note>>
    
    @Insert
    suspend fun insertNote(note: Note)
    
    @Delete
    suspend fun deleteNote(note: Note)
}
