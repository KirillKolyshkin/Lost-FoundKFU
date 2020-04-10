package com.example.lostfoundkfu.features.mainscreen

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.features.createlostobject.CreateLostObjectFragment
import com.example.lostfoundkfu.features.foundlist.FoundListFragment
import com.example.lostfoundkfu.features.lostandfoundselect.LostAndFoundSelectFragment
import com.example.lostfoundkfu.features.lostlist.LostListFragment
import com.example.lostfoundkfu.features.myprofile.MyProfileFragment
import com.example.lostfoundkfu.features.universallist.UniversalListFragment
import com.example.lostfoundkfu.features.universallist.UseCases
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val lostAndFoundFragment = LostAndFoundSelectFragment.newInstance()
    private val lostListFragment = LostListFragment.newInstance()
    private val foundListFragment = FoundListFragment.newInstance()
    private val profileFragment = MyProfileFragment.newInstance()
    private val createLostObjectFragment = CreateLostObjectFragment.newInstance(true)
    private val createFoundObjectFragment = CreateLostObjectFragment.newInstance(false)

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_lost_and_found -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, lostAndFoundFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_lost_list -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, lostListFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_found_list -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, foundListFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, profileFragment)
                        .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    fun openCreateNewLostObjectFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, createLostObjectFragment)
            .commit()
    }

    fun openCreateNewFoundObjectFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, createFoundObjectFragment)
            .commit()
    }

    fun openUniversalList(useCase: UseCases, item : LostItem?, userLink: String?){
        val supposedLostList = UniversalListFragment.newInstance(useCase, item, userLink)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, supposedLostList)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.action_lost_and_found
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, lostAndFoundFragment)
                .commit()
        }
    }
}
