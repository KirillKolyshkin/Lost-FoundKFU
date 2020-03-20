package com.example.lostfoundkfu.features

import android.app.Application
import com.example.lostfoundkfu.features.di.components.AppComponent
import com.example.lostfoundkfu.features.di.components.DaggerAppComponent
import com.example.lostfoundkfu.features.di.modules.AppModule
import com.vk.sdk.VKSdk

class App : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        VKSdk.initialize(applicationContext)
    }

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        }
        return appComponent as AppComponent
    }

    companion object {
        lateinit var instance: App
            private set
    }
}

