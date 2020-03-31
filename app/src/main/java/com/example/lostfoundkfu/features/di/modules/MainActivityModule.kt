package com.example.lostfoundkfu.features.di.modules

import com.example.lostfoundkfu.data.db.DBProvider
import com.example.lostfoundkfu.data.db.UserProvider
import com.example.lostfoundkfu.features.foundlist.FoundListPresenter
import com.example.lostfoundkfu.features.lostlist.LostListPresenter
import com.example.lostfoundkfu.features.myprofile.MyProfilePresenter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideDBProvider() = DBProvider()

    @Provides
    fun providesUserProvider(): UserProvider = UserProvider()

    @Provides
    fun provideLostListPresenter(provider: DBProvider) = LostListPresenter(provider)

    @Provides
    fun provideFoundListPresnter(provider: DBProvider) = FoundListPresenter(provider)

    @Provides
    fun provideMyProfilePresenter(): MyProfilePresenter = MyProfilePresenter()
}
