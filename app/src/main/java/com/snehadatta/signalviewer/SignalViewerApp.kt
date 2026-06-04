package com.snehadatta.signalviewer

import android.app.Application
import com.snehadatta.signalviewer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SignalViewerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SignalViewerApp)
            modules(appModule)
        }
    }
}