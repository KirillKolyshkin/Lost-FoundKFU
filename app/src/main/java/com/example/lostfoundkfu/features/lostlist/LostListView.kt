package com.example.lostfoundkfu.features.lostlist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.lostfoundkfu.data.Items.LostItem

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface LostListView: MvpView {
    fun showLostList(dataList: ArrayList<LostItem>)
    fun getLostList(dataList: ArrayList<LostItem>)
}