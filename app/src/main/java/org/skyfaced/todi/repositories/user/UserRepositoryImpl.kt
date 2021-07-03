package org.skyfaced.todi.repositories.user

import org.skyfaced.todi.database.dao.user.UserDao
import org.skyfaced.todi.database.entities.user.UserEntity
import org.skyfaced.todi.mappers.user.toUser
import org.skyfaced.todi.models.user.User
import org.skyfaced.todi.utils.extensions.asUUID

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    override suspend fun createUser(userEntity: UserEntity): User {
        dao.insert(userEntity)
        return userEntity.toUser()
    }

    override suspend fun getById(id: String): User {
        return dao.getById(id.asUUID()).toUser()
    }
}