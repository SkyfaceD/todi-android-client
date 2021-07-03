package org.skyfaced.todi.database.entities.task

import androidx.room.Entity
import androidx.room.PrimaryKey
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
}