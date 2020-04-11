package com.example.lostfoundkfu.features.di.components

import com.example.lostfoundkfu.features.createlostobject.CreateLostObjectFragment
import com.example.lostfoundkfu.features.di.modules.MainActivityModule
import com.example.lostfoundkfu.features.foundlist.FoundListFragment
import com.example.lostfoundkfu.features.itemdetail.ItemDetailFragment
import com.example.lostfoundkfu.features.lostlist.LostListFragment
import com.example.lostfoundkfu.features.myprofile.MyProfileFragment
import com.example.lostfoundkfu.features.universallist.UniversalListFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun inject(lostListFragment: LostListFragment)
    fun inject(foundListFragment: FoundListFragment)
    fun inject(myProfileFragment: MyProfileFragment)
    fun inject(createLostObjectFragment: CreateLostObjectFragment)
    fun inject(universalListFragment: UniversalListFragment)
    fun inject(itemDetailFragment: ItemDetailFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }
}
