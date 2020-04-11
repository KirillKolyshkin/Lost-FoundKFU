package com.example.lostfoundkfu.features.itemdetail

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.data.db.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class ItemDetailPresenter(private val dbProvider: DBProvider) : MvpPresenter<ItemDetailView>() {
    private val compositeDisposable = CompositeDisposable()

    fun deleteItem(item: LostItem) {
        dbProvider.deleteItem(item)
    }

    override fun destroyView(view: ItemDetailView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
