package org.skyfaced.todi.database.entities.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = UserEntity.TableName)
class UserEntity(
    @PrimaryKey
    val id: UUID,
    val username: String,
    val password: String,
    val createdAt: Date
) {
    companion object {
        const val TableName = "user"
    }
}