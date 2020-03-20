package com.example.lostfoundkfu.features.di.components

import com.example.lostfoundkfu.features.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun loginComponent(): LoginComponent.Builder
}
