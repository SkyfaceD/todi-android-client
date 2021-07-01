package org.skyfaced.todi.di.modules

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.skyfaced.todi.database.ApplicationDatabase
import org.skyfaced.todi.utils.DATABASE_NAME

val databaseModule = module(createdAtStart = true) {
    single { database }

    single { get<ApplicationDatabase>().taskDao }
}

private val Scope.database
    get(): ApplicationDatabase {
        return Room
            .databaseBuilder(androidContext(), ApplicationDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }