package org.skyfaced.todi.repositories.task

import org.skyfaced.todi.database.entities.TaskEntity
import org.skyfaced.todi.models.task.Task

interface TaskRepository {
    suspend fun saveTask(taskEntity: TaskEntity): Task

    fun getById(id: String): Task

    fun getAll(): List<Task>
}