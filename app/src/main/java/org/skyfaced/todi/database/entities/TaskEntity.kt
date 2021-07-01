package org.skyfaced.todi.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.skyfaced.todi.models.task.Task
import org.skyfaced.todi.utils.extensions.toDate
import java.util.*

@Entity(tableName = TaskEntity.TableName)
data class TaskEntity(
    @PrimaryKey
    val id: UUID,
    val title: String,
    val description: String,
    val createdAt: Date
) {
    companion object {
        const val TableName = "task"
    }

    fun toTask() = Task(
        id = id.toString(),
        title = title,
        description = description,
        createdAt = createdAt.time.toDate()
    )
}