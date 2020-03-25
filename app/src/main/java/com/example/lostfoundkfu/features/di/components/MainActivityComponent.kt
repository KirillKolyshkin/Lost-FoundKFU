package com.example.lostfoundkfu.features.di.components

import com.example.lostfoundkfu.features.di.modules.MainActivityModule
import com.example.lostfoundkfu.features.foundlist.FoundListFragment
import com.example.lostfoundkfu.features.lostlist.LostListFragment
import dagger.Subcomponent

@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {

    fun inject(lostListFragment: LostListFragment)
    fun inject(foundListFragment: FoundListFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }
}
