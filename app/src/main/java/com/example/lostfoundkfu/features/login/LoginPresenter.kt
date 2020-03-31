package com.example.lostfoundkfu.features.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.db.UserProvider

@InjectViewState
class LoginPresenter(private val userProvider: UserProvider): MvpPresenter<LoginView>() {

    fun login() = viewState.loginVK()

    fun setUser() = userProvider.setUser()
}
