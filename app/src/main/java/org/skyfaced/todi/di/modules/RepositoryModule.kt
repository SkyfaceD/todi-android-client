package org.skyfaced.todi.di.modules

import org.koin.dsl.module
import org.skyfaced.todi.database.ApplicationDatabase
import org.skyfaced.todi.repositories.task.TaskRepository
import org.skyfaced.todi.repositories.task.TaskRepositoryImpl

val repositoryModule = module {
    single<TaskRepository> { TaskRepositoryImpl(get<ApplicationDatabase>().taskDao) }
}