package org.skyfaced.todi.database.dao.user

import androidx.room.Dao
import androidx.room.Query
import org.skyfaced.todi.database.dao.BaseDao
import org.skyfaced.todi.database.entities.user.UserEntity
import java.util.*

@Dao
abstract class UserDao : BaseDao<UserEntity> {
    @Query("SELECT * FROM ${UserEntity.TableName} WHERE id = :id")
    abstract suspend fun getById(id: UUID): UserEntity

    @Query("SELECT * FROM ${UserEntity.TableName}")
    abstract suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM ${UserEntity.TableName} WHERE username = :username")
    abstract suspend fun getByUsername(username: String): UserEntity?

    @Query("SELECT EXISTS(SELECT * FROM ${UserEntity.TableName} WHERE username = :username AND password = :password)")
    abstract suspend fun checkCredentials(username: String, password: String): Boolean
}