package org.skyfaced.todi.database.converters

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun toUUID(string: String): UUID = UUID.fromString(string)
}