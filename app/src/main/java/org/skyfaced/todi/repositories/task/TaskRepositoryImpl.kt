package org.skyfaced.todi.repositories.task

import org.skyfaced.todi.database.dao.task.TaskDao
import org.skyfaced.todi.database.entities.task.TaskEntity
import org.skyfaced.todi.mappers.task.toTask
import org.skyfaced.todi.models.task.Task
import java.util.*

class TaskRepositoryImpl(private val dao: TaskDao) : TaskRepository {
    override suspend fun saveTask(taskEntity: TaskEntity): Task {
        dao.insert(taskEntity)
        return taskEntity.toTask()
    }

    override suspend fun getById(id: String): Task {
        return dao.getById(UUID.fromString(id)).toTask()
    }

    override suspend fun getAll(): List<Task> {
        return dao.getAll().map(TaskEntity::toTask)
    }
}