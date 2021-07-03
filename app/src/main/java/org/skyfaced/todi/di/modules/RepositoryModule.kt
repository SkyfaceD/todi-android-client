package org.skyfaced.todi.di.modules

import org.koin.dsl.module
import org.skyfaced.todi.database.ApplicationDatabase
import org.skyfaced.todi.repositories.task.TaskRepository
import org.skyfaced.todi.repositories.task.TaskRepositoryImpl
import org.skyfaced.todi.repositories.user.UserRepository
import org.skyfaced.todi.repositories.user.UserRepositoryImpl

val repositoryModule = module {
    single<TaskRepository> {
        TaskRepositoryImpl(get<ApplicationDatabase>().taskDao)
    }

    single<UserRepository> {
        UserRepositoryImpl(get<ApplicationDatabase>().userDao)
    }
}