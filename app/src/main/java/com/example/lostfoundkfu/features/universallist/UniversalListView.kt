package com.example.lostfoundkfu.features.universallist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.lostfoundkfu.data.Items.LostItem

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface UniversalListView : MvpView {
    fun showList()
    fun getList(dataList: ArrayList<LostItem>)
}
