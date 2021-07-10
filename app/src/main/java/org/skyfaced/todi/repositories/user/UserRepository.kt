package org.skyfaced.todi.repositories.user

import org.skyfaced.todi.models.user.User

interface UserRepository {
    suspend fun createUser(username: String, password: String): User

    suspend fun getById(id: String): User

    suspend fun getAll(): List<User>

    suspend fun isUsernameAvailable(username: String): Boolean

    suspend fun checkCredentials(username: String, password: String): Boolean
}