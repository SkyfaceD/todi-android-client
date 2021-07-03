package org.skyfaced.todi.repositories.task

import org.skyfaced.todi.database.entities.task.TaskEntity
import org.skyfaced.todi.models.task.Task

interface TaskRepository {
    suspend fun saveTask(taskEntity: TaskEntity): Task

    suspend fun getById(id: String): Task

    suspend fun getAll(): List<Task>
}