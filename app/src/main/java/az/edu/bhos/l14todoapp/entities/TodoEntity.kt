package az.edu.bhos.l14todoapp.entities

data class TodoEntity(
    val id: String,
    val title: String,
    val completed: Boolean,
    val weekday: String,
    val createdAt: String
)