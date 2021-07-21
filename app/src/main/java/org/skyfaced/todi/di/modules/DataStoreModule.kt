package org.skyfaced.todi.di.modules

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.skyfaced.todi.datastore.user.UserPreferencesRepository
import org.skyfaced.todi.datastore.user.UserPreferencesRepositoryImpl
import org.skyfaced.todi.datastore.userPreferences

val dataStoreModule = module {
    single<UserPreferencesRepository> {
        UserPreferencesRepositoryImpl(androidContext().userPreferences)
    }
}