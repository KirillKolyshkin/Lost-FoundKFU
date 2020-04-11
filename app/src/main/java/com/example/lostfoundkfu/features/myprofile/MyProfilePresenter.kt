package com.example.lostfoundkfu.features.myprofile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.db.UserProvider

@InjectViewState
class MyProfilePresenter : MvpPresenter<MyProfileView>() {

    override fun onFirstViewAttach() {
        UserProvider.curUser?.let { viewState.setUserInfo(it) }
        UserProvider.curUserPic?.let { viewState.setPic(it) }
    }

    fun openVK(){
        UserProvider.curUser?.screen_name?.let { viewState.openVK(it) }
    }

}
