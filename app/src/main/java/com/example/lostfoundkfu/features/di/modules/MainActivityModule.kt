package com.example.lostfoundkfu.features.di.modules

import android.content.SharedPreferences
import com.example.lostfoundkfu.data.db.DBProvider
import com.example.lostfoundkfu.data.db.UserProvider
import com.example.lostfoundkfu.features.createlostobject.CreateLostObjectPresenter
import com.example.lostfoundkfu.features.foundlist.FoundListPresenter
import com.example.lostfoundkfu.features.itemdetail.ItemDetailPresenter
import com.example.lostfoundkfu.features.lostlist.LostListPresenter
import com.example.lostfoundkfu.features.myprofile.MyProfilePresenter
import com.example.lostfoundkfu.features.universallist.UniversalListPresenter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideDBProvider(
        firebaseFirestore: FirebaseFirestore,
        sharedPreferences: SharedPreferences,
        firebaseStorage: FirebaseStorage
    ) = DBProvider(firebaseFirestore, sharedPreferences, firebaseStorage)

    @Provides
    fun providesUserProvider(): UserProvider = UserProvider()

    @Provides
    fun provideLostListPresenter(provider: DBProvider) = LostListPresenter(provider)

    @Provides
    fun provideFoundListPresnter(provider: DBProvider) = FoundListPresenter(provider)

    @Provides
    fun provideMyProfilePresenter(): MyProfilePresenter = MyProfilePresenter()

    @Provides
    fun provideCreateLostObjectPresnter(provider: DBProvider): CreateLostObjectPresenter =
        CreateLostObjectPresenter(provider)

    @Provides
    fun provideUniversalListPresenter(provider: DBProvider): UniversalListPresenter =
        UniversalListPresenter(provider)

    @Provides
    fun provideItemDetailFragment(provider: DBProvider): ItemDetailPresenter =
        ItemDetailPresenter(provider)
}
