package org.skyfaced.todi.repositories.user

import org.skyfaced.todi.database.dao.user.UserDao
import org.skyfaced.todi.database.entities.user.UserEntity
import org.skyfaced.todi.mappers.user.toUser
import org.skyfaced.todi.models.user.User
import org.skyfaced.todi.utils.extensions.asUUID
import java.util.*

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    override suspend fun createUser(username: String, password: String): User {
        val userEntity = UserEntity(
            UUID.randomUUID(),
            username,
            password,
            Date(System.currentTimeMillis())
        )
        dao.insert(userEntity)
        return userEntity.toUser()
    }

    override suspend fun getById(id: String): User {
        return dao.getById(id.asUUID()).toUser()
    }

    override suspend fun getByUsername(username: String): User? {
        return dao.getByUsername(username)?.toUser()
    }

    override suspend fun getAll(): List<User> {
        return dao.getAll().map(UserEntity::toUser)
    }

    /**
     * @return true if username is available, otherwise false
     */
    //FIXME
    override suspend fun isUsernameAvailable(username: String): Boolean {
        return dao.getByUsername(username) != null
    }

    override suspend fun checkCredentials(username: String, password: String): Boolean {
        return dao.checkCredentials(username, password)
    }
}