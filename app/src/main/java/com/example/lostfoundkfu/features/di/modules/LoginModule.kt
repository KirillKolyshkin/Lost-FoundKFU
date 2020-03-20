package com.example.lostfoundkfu.features.di.modules

import com.example.lostfoundkfu.features.login.LoginPresenter
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

    @Provides
    fun providesLoginPresenter(): LoginPresenter = LoginPresenter()
}
