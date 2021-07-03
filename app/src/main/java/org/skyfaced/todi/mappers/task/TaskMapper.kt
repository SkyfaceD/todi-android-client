package org.skyfaced.todi.mappers.task

import org.skyfaced.todi.database.entities.task.TaskEntity
import org.skyfaced.todi.models.task.Task
import org.skyfaced.todi.utils.extensions.toDate

fun TaskEntity.toTask() = Task(
    id = id.toString(),
    title = title,
    description = description,
    createdAt = createdAt.time.toDate()
)