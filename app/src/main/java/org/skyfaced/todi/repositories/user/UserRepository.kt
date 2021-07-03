package org.skyfaced.todi.repositories.user

import org.skyfaced.todi.database.entities.user.UserEntity
import org.skyfaced.todi.models.user.User

interface UserRepository {
    suspend fun createUser(userEntity: UserEntity): User

    suspend fun getById(id: String): User
}