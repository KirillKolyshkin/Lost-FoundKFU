package com.example.lostfoundkfu.features.itemdetail

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.lostfoundkfu.data.Items.BuildingWithoutFlag

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ItemDetailView : MvpView {
    fun getBuildings(list: ArrayList<BuildingWithoutFlag>)
}
