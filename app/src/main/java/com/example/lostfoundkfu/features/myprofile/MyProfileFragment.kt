package com.example.lostfoundkfu.features.myprofile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.db.UserProvider
import com.example.lostfoundkfu.data.user.User
import com.example.lostfoundkfu.features.App
import com.example.lostfoundkfu.features.mainscreen.MainActivity
import com.example.lostfoundkfu.features.universallist.UseCases
import kotlinx.android.synthetic.main.my_profile_fragment.*
import javax.inject.Inject

class MyProfileFragment : MvpAppCompatFragment(), MyProfileView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MyProfilePresenter

    @ProvidePresenter
    fun initPresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance
            .getAppComponent()
            .mainActivityComponent()
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.my_profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_vk.setOnClickListener { presenter.openVK() }
        initClickListener()
    }

    private fun initClickListener(){
        val userLink = UserProvider.curUser?.screen_name!!
        btn_my_found_list.setOnClickListener { openFoundList(userLink) }
        btn_my_lost_list.setOnClickListener { openLostList(userLink) }
    }

    override fun setUserInfo(user: User) {
        tv_name.text = NAME_FORMAT.format(user.first_name, user.last_name)
    }

    override fun openVK(userLink: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://vk.com/" + userLink))
        startActivity(browserIntent)
    }

    override fun setPic(url: String) {
        context?.let { Glide.with(it).load(url).into(profile_image) }
    }

    override fun openFoundList(userLink: String) =
        (activity as MainActivity).openUniversalList(UseCases.MyFoundList, null, userLink)


    override fun openLostList(userLink: String) =
        (activity as MainActivity).openUniversalList(UseCases.MyLostList, null, userLink)

    companion object {
        private const val NAME_FORMAT = "%s %s"
        fun newInstance(): MyProfileFragment =
            MyProfileFragment()
    }
}
