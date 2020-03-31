package com.example.lostfoundkfu.features.myprofile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.lostfoundkfu.data.user.User

@StateStrategyType(AddToEndSingleStrategy::class)
interface MyProfileView : MvpView {
    fun setUserInfo(user: User)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openVK(userLink: String)
    fun setPic(url: String)
    fun openFoundList(userLink: String)
    fun openLostList(userLink: String)
}
