package com.example.miniproyecto_persistenciadatos

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.launch

@Composable
fun DiaryApp(
    repository: NoteRepository,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Configuración",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))
                NavigationDrawerItem(
                    label = { Text("Modo Oscuro") },
                    selected = false,
                    onClick = { onThemeChange(!isDarkTheme) },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                    badge = {
                        Switch(
                            checked = isDarkTheme,
                            onCheckedChange = { onThemeChange(it) }
                        )
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "notes"
        ) {
            composable("notes") {
                NotesScreen(
                    viewModel = viewModel,
                    onNavigateToAddNote = { navController.navigate("add_note") },
                    onOpenDrawer = { scope.launch { drawerState.open() } }
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
}