package com.example.lostfoundkfu.features.lostlist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.db.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class LostListPresenter(private val dbProvider: DBProvider) : MvpPresenter<LostListView>() {
    private val compositeDisposable = CompositeDisposable()

    fun setLostList() {
        val disposable = dbProvider.getTestLostList().subscribeBy {
            viewState.showLostList(it)
        }
        compositeDisposable.add(disposable)
    }

    fun getLostList(){
        val disposable = dbProvider.getTestLostList().subscribeBy {
            viewState.getLostList(it)
        }
        compositeDisposable.add(disposable)
    }

    override fun destroyView(view: LostListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}