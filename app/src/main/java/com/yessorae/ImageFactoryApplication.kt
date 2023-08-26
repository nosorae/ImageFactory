package com.yessorae

import android.app.Application
import com.yessorae.common.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ImageFactoryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Logger.initialize()
    }
}
