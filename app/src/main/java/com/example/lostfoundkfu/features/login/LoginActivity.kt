package com.example.lostfoundkfu.features.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.features.App
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.util.VKUtil
import kotlinx.android.synthetic.main.login_activity.*
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
        setContentView(R.layout.login_activity)
        if (VKSdk.isLoggedIn())
            enterApp()
        btn_log_in.setOnClickListener { loginPresenter.login() }
    }

    private fun enterApp(){
        loginPresenter.setUser()
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (VKSdk.onActivityResult(requestCode, resultCode, data, object :
                VKCallback<VKAccessToken> {
                override fun onResult(res: VKAccessToken) {
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
                    enterApp()
                }

                override fun onError(error: VKError) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }
            })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun loginVK() {
        VKSdk.login(this, VKScope.PHOTOS)
    }
}
