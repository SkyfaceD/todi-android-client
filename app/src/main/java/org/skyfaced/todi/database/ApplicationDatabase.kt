package org.skyfaced.todi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.skyfaced.todi.database.converters.DateConverter
import org.skyfaced.todi.database.converters.UUIDConverter
import org.skyfaced.todi.database.dao.task.TaskDao
import org.skyfaced.todi.database.dao.user.UserDao
import org.skyfaced.todi.database.entities.task.TaskEntity
import org.skyfaced.todi.database.entities.user.UserEntity

@Database(entities = [TaskEntity::class, UserEntity::class], version = 3, exportSchema = true)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
    abstract val userDao: UserDao
}
