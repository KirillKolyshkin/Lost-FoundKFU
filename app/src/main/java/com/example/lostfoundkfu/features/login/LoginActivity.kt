package com.example.lostfoundkfu.features.login

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.features.App
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    @ProvidePresenter
    fun initPresenter() = loginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance
            .getAppComponent()
            .loginComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun loginVK() {
        VKSdk.login(this, VKScope.PHOTOS)
    }
}
