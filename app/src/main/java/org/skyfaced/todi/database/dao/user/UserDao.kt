package org.skyfaced.todi.database.dao.user

import androidx.room.Query
import org.skyfaced.todi.database.dao.BaseDao
import org.skyfaced.todi.database.entities.user.UserEntity
import java.util.*

abstract class UserDao : BaseDao<UserEntity> {
    @Query("SELECT * FROM ${UserEntity.TableName} WHERE id = :id")
    abstract suspend fun getById(id: UUID): UserEntity
}