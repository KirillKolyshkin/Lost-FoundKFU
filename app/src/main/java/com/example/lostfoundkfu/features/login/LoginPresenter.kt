package com.example.lostfoundkfu.features.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter

@InjectViewState
class LoginPresenter: MvpPresenter<LoginView>() {

    fun login() = viewState.loginVK()

}
