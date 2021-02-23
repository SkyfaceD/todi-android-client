package org.skyfaced.todi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.skyfaced.todi.di.modules.applicationModule
import org.skyfaced.todi.di.modules.viewModelModule

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(applicationModule, viewModelModule)
        }
    }
}