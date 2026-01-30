# Explicación de `NoteDatabase` (en español)

Este documento explica de forma clara y concisa la clase `NoteDatabase` usada con la librería Room en Android. Está pensado para un desarrollador que conoce Kotlin/Android pero no está familiarizado con este código concreto.

## Propósito general

`NoteDatabase` extiende `RoomDatabase` y actúa como el punto central para acceder a la base de datos SQLite de la app. Su responsabilidad es exponer los DAO (Data Access Objects) y gestionar la creación y la instancia única (singleton) de la base de datos.

## Método abstracto DAO

La clase expone un método abstracto que devuelve el DAO correspondiente, por ejemplo:

```kotlin
abstract fun noteDao(): NoteDao
```

Room genera la implementación de este método en tiempo de compilación. Desde otras partes de la app se obtiene la instancia del DAO llamando `noteDao()` sobre la instancia de `NoteDatabase` para ejecutar consultas, inserciones, actualizaciones y borrados.

## Patrón Singleton y inicialización segura

Para evitar múltiples instancias de la base de datos (lo cual consume recursos y puede causar errores), `NoteDatabase` implementa un singleton seguro para hilos. Algunos fragmentos clave:

- Declaración de la instancia con visibilidad entre hilos:

```kotlin
@Volatile
private var INSTANCE: NoteDatabase? = null
```

- Inicialización perezosa y sincronizada (extracto):

```kotlin
fun getDatabase(context: Context): NoteDatabase {
    return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()
        INSTANCE = instance
        instance
    }
}
```

La lógica comprueba si `INSTANCE` ya existe y, si no, entra en un bloque `synchronized` para que solo un hilo la cree. Se usa `context.applicationContext` para evitar fugas de memoria.

## Notas prácticas y mejoras recomendadas

- Migraciones: el builder mostrado crea la base de datos sin migraciones. En producción debes agregar migraciones con `.addMigrations(...)` o, si aceptas perder datos en ciertos cambios, usar `.fallbackToDestructiveMigration()` con cuidado.

- Callback y pre-poblado: puedes añadir `.addCallback(...)` al `databaseBuilder` para precargar datos o ejecutar lógica al crear/abrir la DB.

- Inicialización y DI: conviene acceder a la base de datos desde una clase `Application` o inyectarla con un contenedor DI (Hilt/DI personalizado) para un único punto de acceso.

- Operaciones de E/S: las operaciones de base de datos deben ejecutarse fuera del hilo UI (por ejemplo, usando coroutines y `suspend` en los DAOs o `withContext(Dispatchers.IO)`).

- Multi-proceso: si la app usa multiproceso, la estrategia de singleton en memoria no es suficiente; revisa la configuración de Room para multiproceso.

## Ejemplo mínimo de uso desde un `ViewModel`

Fragmento que muestra cómo usar el DAO con coroutines desde un `ViewModel`:

```kotlin
class NotesViewModel(private val db: NoteDatabase): ViewModel() {
    private val noteDao = db.noteDao()

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteDao.insert(note)
    }
}
```

Este ejemplo ilustra obtener el DAO y ejecutar una operación en `Dispatchers.IO` desde `viewModelScope`.

---

Si quieres que añada esto a `README.md` en lugar de `EXPLICACION.md`, que incluya una sección de ejemplos más amplia, o que modifique la clase para añadir una migración o callback de precarga, dímelo y lo hago.
