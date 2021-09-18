package com.demo.takenote

import android.app.Application
import com.demo.takenote.di.dbModule
import com.demo.takenote.di.repositoryModule
import com.demo.takenote.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *  Create by ThanhPQ
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(repositoryModule, viewModelModule, dbModule))
        }
    }
}