package com.phidalgo.crudcategories

import android.app.Application
import com.phidalgo.crudcategories.di.appModule
import com.phidalgo.crudcategories.di.authModule
import com.phidalgo.crudcategories.di.pokemonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@MyApp)
            modules(appModule, authModule, pokemonModule)
        }
    }
}
