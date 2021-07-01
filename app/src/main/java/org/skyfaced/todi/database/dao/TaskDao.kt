package org.skyfaced.todi.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.skyfaced.todi.database.BaseDao
import org.skyfaced.todi.database.entities.TaskEntity
import java.util.*

@Dao
abstract class TaskDao : BaseDao<TaskEntity> {
    @Query("SELECT * FROM ${TaskEntity.TableName} WHERE id = :id")
    abstract fun getById(id: UUID): TaskEntity

    @Query("SELECT * FROM ${TaskEntity.TableName} ORDER BY createdAt DESC")
    abstract fun getAll(): List<TaskEntity>
}