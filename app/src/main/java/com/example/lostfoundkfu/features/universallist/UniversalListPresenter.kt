package com.example.lostfoundkfu.features.universallist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.data.db.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class UniversalListPresenter(private val dbProvider: DBProvider) :
    MvpPresenter<UniversalListView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getSupposedLostList(item: LostItem) {
        val disposable = dbProvider.getSupposedLostList(item).subscribeBy {
            viewState.getList(it)
        }
        compositeDisposable.add(disposable)
    }

    fun getSupposedFoundList(item: LostItem) {
        val disposable = dbProvider.getSupposedFoundList(item).subscribeBy {
            viewState.getList(it)
        }
        compositeDisposable.add(disposable)
    }

    fun getMyLostList(userLink: String) {
        val disposable = dbProvider.getMyLostList(userLink).subscribeBy {
            viewState.getList(it)
        }
        compositeDisposable.add(disposable)
    }

    fun getMyFoundList(userLink: String) {
        val disposable = dbProvider.getMyFoundList(userLink).subscribeBy {
            viewState.getList(it)
        }
        compositeDisposable.add(disposable)
    }

    fun setList() {
        viewState.showList()
    }

    override fun destroyView(view: UniversalListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
