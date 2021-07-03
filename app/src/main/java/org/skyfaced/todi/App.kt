package org.skyfaced.todi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.skyfaced.todi.di.modules.applicationModule
import org.skyfaced.todi.di.modules.repositoryModule
import org.skyfaced.todi.di.modules.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(applicationModule, repositoryModule, viewModelModule)
        }
    }
}