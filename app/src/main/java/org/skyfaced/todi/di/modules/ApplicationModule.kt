package org.skyfaced.todi.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.skyfaced.todi.database.ApplicationDatabase
import org.skyfaced.todi.utils.DATABASE_NAME
import org.skyfaced.todi.utils.SP_NAME
import org.skyfaced.todi.utils.markdown.Markdown
import org.skyfaced.todi.utils.markdown.MarkdownImpl

val applicationModule = module(createdAtStart = true) {
    single { sharedPreferences }

    single<Markdown> { MarkdownImpl(androidContext()) }

    single { database }
}

private val Scope.sharedPreferences
    get(): SharedPreferences {
        return androidContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

private val Scope.database
    get(): ApplicationDatabase {
        return Room
            .databaseBuilder(androidContext(), ApplicationDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }