package com.example.lostfoundkfu.features.foundlist

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.lostfoundkfu.data.Items.LostItem

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FoundListView: MvpView {
    fun showFoundList(dataList: ArrayList<LostItem>)
    fun getFoundList(dataList: ArrayList<LostItem>)
}
