package com.gustavofleck.sicrediteste.application

import android.app.Application
import com.gustavofleck.sicrediteste.di.eventModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EventsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EventsApplication)
            modules(eventModules)
        }
    }
}