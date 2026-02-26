package com.example.miniproyecto_persistenciadatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.miniproyecto_persistenciadatos.data.NoteDatabase
import com.example.miniproyecto_persistenciadatos.data.NoteRepository
import com.example.miniproyecto_persistenciadatos.data.UserPreferencesRepository
import com.example.miniproyecto_persistenciadatos.ui.NoteViewModel
import com.example.miniproyecto_persistenciadatos.ui.screens.AddNoteScreen
import com.example.miniproyecto_persistenciadatos.ui.screens.EditNoteScreen
import com.example.miniproyecto_persistenciadatos.ui.screens.NotesScreen
import com.example.miniproyecto_persistenciadatos.ui.screens.SettingsScreen
import com.example.miniproyecto_persistenciadatos.ui.theme.MiniProyectoPersistenciaDatosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = NoteDatabase.getDatabase(applicationContext)
        val repository = NoteRepository(database.noteDao())
        val preferencesRepository = UserPreferencesRepository(applicationContext)

        setContent {
            DiaryApp(repository, preferencesRepository)
        }
    }
}

@Composable
fun DiaryApp(repository: NoteRepository, preferencesRepository: UserPreferencesRepository) {
    val navController = rememberNavController()

    // Crear factory con ambos repositorios
    val viewModelFactory = remember {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(repository, preferencesRepository) as T
            }
        }
    }

    val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)

    // Observar el tema oscuro desde las preferencias del usuario
    val preferences by viewModel.userPreferences.collectAsState()

    MiniProyectoPersistenciaDatosTheme(darkTheme = preferences.isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = "notes"
            ) {
                composable("notes") {
                    NotesScreen(
                        viewModel = viewModel,
                        onNavigateToAddNote = { navController.navigate("add_note") },
                        onNavigateToEditNote = { noteId ->
                            navController.navigate("edit_note/$noteId")
                        },
                        onNavigateToSettings = { navController.navigate("settings") }
                    )
                }

                composable("add_note") {
                    AddNoteScreen(
                        viewModel = viewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                composable(
                    route = "edit_note/{noteId}",
                    arguments = listOf(navArgument("noteId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
                    EditNoteScreen(
                        viewModel = viewModel,
                        noteId = noteId,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                composable("settings") {
                    SettingsScreen(
                        viewModel = viewModel,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
