package org.skyfaced.todi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.skyfaced.todi.database.converters.DateConverter
import org.skyfaced.todi.database.converters.UUIDConverter
import org.skyfaced.todi.database.dao.TaskDao
import org.skyfaced.todi.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 2, exportSchema = true)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao
}
