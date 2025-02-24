package com.hogwartslegacy

import android.app.Application
import com.hogwartslegacy.di.hogwartsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HogwartsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HogwartsApplication)
            androidLogger(Level.DEBUG)
            modules(hogwartsModule)
        }
    }
}