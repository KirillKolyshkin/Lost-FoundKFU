package com.example.lostfoundkfu.features.mainscreen

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.features.lostandfoundselect.LostAndFoundSelectFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity: AppCompatActivity() {

    private val lostAndFoundFragment = LostAndFoundSelectFragment.newInstance()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_lost_and_found -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, lostAndFoundFragment)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_lost_list -> {
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.container, searchPartyListFragment)
//                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_found_list -> {
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.container, partyListFragment)
//                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_profile -> {
                /*supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, profileFragment)
                    .commit()*/
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.action_profile
        /*if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, partyListFragment)
                .commit()
        }*/
    }
}
