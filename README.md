# Diario Personal - Android App

Una aplicación de Diario Personal desarrollada en Android con Kotlin y Jetpack Compose, utilizando Room para persistencia de datos y DataStore para preferencias del usuario.

## 🚀 Características

### Funcionalidades Principales
- ✅ **Crear notas**: Agregar entradas con título, contenido, fecha automática y estado de ánimo
- ✅ **Borrar notas**: Eliminar notas existentes con un solo toque
- ✅ **Visualizar notas**: Lista de todas las notas ordenadas por fecha (más recientes primero)
- ✅ **Estados de ánimo**: 5 opciones con emojis (Neutral 😐, Feliz 😊, Triste 😢, Emocionado 🤩, Pensativo 🤔)
- ✅ **Validación**: No permite guardar notas con título o contenido vacíos

### Persistencia de Datos
- **Room Database**: Almacenamiento local de notas
- **DataStore**: Preferencias del usuario (tamaño de fuente, tema, estado de ánimo predeterminado)

## 🏗️ Arquitectura

El proyecto sigue una arquitectura limpia con separación de capas:

```
app/src/main/java/com/example/miniproyecto_persistenciadatos/
├── data/                              # Capa de datos
│   ├── Note.kt                        # Entidad Room
│   ├── NoteDao.kt                     # Data Access Object
│   ├── NoteDatabase.kt                # Database Room
│   ├── NoteRepository.kt              # Repositorio de notas
│   ├── UserPreferences.kt             # Clase de preferencias
│   └── UserPreferencesRepository.kt   # Repositorio de preferencias
├── ui/                                # Capa de presentación
│   ├── NoteViewModel.kt               # ViewModel con lógica de negocio
│   ├── screens/
│   │   ├── NotesScreen.kt            # Pantalla principal
│   │   └── AddNoteScreen.kt          # Pantalla de creación
│   └── theme/                         # Tema Material Design 3
└── MainActivity.kt                    # Punto de entrada y navegación
```

## 🔧 Tecnologías Utilizadas

### Core
- **Kotlin 1.9.22**: Lenguaje de programación
- **Jetpack Compose**: UI declarativa moderna
- **Material Design 3**: Sistema de diseño

### Jetpack Libraries
- **Room 2.6.1**: Base de datos SQLite
- **DataStore 1.0.0**: Almacenamiento de preferencias
- **Navigation Compose 2.7.6**: Navegación entre pantallas
- **ViewModel**: Gestión de estado
- **Coroutines & Flow**: Programación asíncrona y reactiva

### Build Tools
- **Android Gradle Plugin 8.3.0**
- **KSP (Kotlin Symbol Processing)**: Procesamiento de anotaciones de Room

## 📱 Pantallas

### Pantalla Principal (NotesScreen)
- **TopAppBar** con título "Mi Diario Personal"
- **FloatingActionButton** para agregar nuevas notas
- **LazyColumn** con lista de notas en cards
- Cada nota muestra:
  - Título (Typography.titleLarge)
  - Fecha formateada (dd/MM/yyyy HH:mm)
  - Contenido (máximo 3 líneas con ellipsis)
  - Estado de ánimo con emoji
  - Botón de eliminar
- Mensaje de estado vacío cuando no hay notas

### Pantalla Crear Nota (AddNoteScreen)
- **TopAppBar** con botón de volver
- **OutlinedTextField** para título (línea única)
- **OutlinedTextField** para contenido (multilínea)
- **RadioButtons** para seleccionar estado de ánimo
- **Button** "Guardar Nota" (habilitado solo con datos válidos)

## 🗄️ Modelo de Datos

### Entidad Note
```kotlin
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: Long,              // Timestamp en milisegundos
    val mood: String             // "neutral", "happy", "sad", "excited", "thoughtful"
)
```

### UserPreferences
```kotlin
data class UserPreferences(
    val fontSize: Int = 16,
    val isDarkTheme: Boolean = false,
    val defaultMood: String = "neutral"
)
```

## 🔄 Flujo de Datos

1. **UI** → Eventos del usuario (crear, eliminar nota)
2. **ViewModel** → Procesa eventos y actualiza StateFlow
3. **Repository** → Abstrae acceso a datos
4. **DAO/DataStore** → Operaciones de persistencia
5. **Flow** → Emite cambios reactivos a la UI

## 🚦 Requisitos del Sistema

- **minSdk**: 24 (Android 7.0)
- **targetSdk**: 34 (Android 14)
- **compileSdk**: 34
- **JVM**: Java 11

## 🛠️ Instalación y Compilación

### Prerrequisitos
- Android Studio Hedgehog o superior
- JDK 11 o superior
- Android SDK 34

### Pasos
1. Clonar el repositorio:
```bash
git clone https://github.com/riggssssss/MiniProyectoPersistenciaDatos.git
cd MiniProyectoPersistenciaDatos
```

2. Abrir el proyecto en Android Studio

3. Sincronizar dependencias (Gradle sync)

4. Ejecutar en emulador o dispositivo:
```bash
./gradlew installDebug
```

## 📝 Uso

1. **Crear una nota**:
   - Pulsar el botón flotante "+" en la pantalla principal
   - Ingresar título y contenido
   - Seleccionar estado de ánimo
   - Pulsar "Guardar Nota"

2. **Ver notas**:
   - Las notas se muestran automáticamente en la pantalla principal
   - Ordenadas por fecha (más recientes primero)

3. **Eliminar una nota**:
   - Pulsar el icono de basura en cualquier nota

## 🔒 Seguridad

- No se almacenan datos sensibles sin encriptar
- DataStore utiliza protección a nivel de sistema
- Room implementa consultas parametrizadas (previene SQL injection)
- No hay permisos especiales requeridos

## 🧪 Testing

El proyecto está diseñado con arquitectura testeable:
- **Unit Tests**: ViewModel, Repository
- **Integration Tests**: Room DAO
- **UI Tests**: Compose testing (pendiente implementación)

## 📄 Licencia

Este proyecto es un mini proyecto educativo desarrollado para demostrar el uso de Room y DataStore en Android.

## 👥 Autor

Desarrollado como parte del curso de Persistencia de Datos en Android.

## 🤝 Contribuciones

Este es un proyecto educativo Creado por Adrían García y Jose Aranda.

## 📚 Recursos

- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
