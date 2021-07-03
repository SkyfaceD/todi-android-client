package org.skyfaced.todi.mappers.user

import org.skyfaced.todi.database.entities.user.UserEntity
import org.skyfaced.todi.models.user.User

fun UserEntity.toUser() = User(
    id = id.toString(),
    username = username
)