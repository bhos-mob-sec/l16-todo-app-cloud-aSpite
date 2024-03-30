package az.edu.bhos.l14todoapp.data.dto

import az.edu.bhos.l14todoapp.entities.TodoEntity
import kotlinx.serialization.Serializable

@Serializable
data class TodoRemoteDto(
    val id: String,
    val title: String,
    val completed: Boolean,
    val weekday: String,
    val createdAt: String
) {
    fun toEntity() = TodoEntity(
        id = this.id,
        title = this.title,
        completed = this.completed,
        weekday = this.weekday,
        createdAt = this.createdAt
    )
}