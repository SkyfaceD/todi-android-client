package org.skyfaced.todi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.skyfaced.todi.di.modules.applicationModule
import org.skyfaced.todi.di.modules.databaseModule
import org.skyfaced.todi.di.modules.repositoryModule
import org.skyfaced.todi.di.modules.viewModelModule

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(applicationModule, databaseModule, repositoryModule, viewModelModule)
        }
    }
}