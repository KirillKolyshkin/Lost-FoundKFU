package com.example.lostfoundkfu.features.di.modules

import com.example.lostfoundkfu.data.db.UserProvider
import com.example.lostfoundkfu.features.login.LoginPresenter
import dagger.Module
import dagger.Provides

@Module
class LoginModule {


    @Provides
    fun providesUserProvider(): UserProvider = UserProvider()

    @Provides
    fun providesLoginPresenter(userProvider: UserProvider): LoginPresenter = LoginPresenter(userProvider)
}
