package com.example.miniproyecto_persistenciadatos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miniproyecto_persistenciadatos.data.NoteRepository
import com.example.miniproyecto_persistenciadatos.ui.NoteViewModel
import com.example.miniproyecto_persistenciadatos.ui.screens.AddNoteScreen
import com.example.miniproyecto_persistenciadatos.ui.screens.NotesScreen

@Composable
fun DiaryApp(repository: NoteRepository) {
    val navController = rememberNavController()

    // Create factory outside of recomposition
    val viewModelFactory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repository) as T
            }
        }
    }

    val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)

    NavHost(
        navController = navController,
        startDestination = "notes"
    ) {
        composable("notes") {
            NotesScreen(
                viewModel = viewModel,
                onNavigateToAddNote = { navController.navigate("add_note") }
            )
        }

        composable("add_note") {
            AddNoteScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}