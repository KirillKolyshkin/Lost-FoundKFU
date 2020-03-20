package com.example.lostfoundkfu.features.di.components

import com.example.lostfoundkfu.features.di.modules.LoginModule
import com.example.lostfoundkfu.features.login.LoginActivity
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])

interface LoginComponent {
    fun inject(loginActivity: LoginActivity)

    @Subcomponent.Builder
    interface Builder {
        fun build(): LoginComponent
    }
}

