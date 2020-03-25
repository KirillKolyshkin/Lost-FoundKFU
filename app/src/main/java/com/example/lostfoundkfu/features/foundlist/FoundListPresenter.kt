package com.example.lostfoundkfu.features.foundlist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.db.DBProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@InjectViewState
class FoundListPresenter(private val dbProvider: DBProvider) : MvpPresenter<FoundListView>() {
    private val compositeDisposable = CompositeDisposable()

    fun setFoundList() {
        val disposable = dbProvider.getTestFoundList().subscribeBy {
            viewState.showFoundList(it)
        }
        compositeDisposable.add(disposable)
    }

    fun getFoundList(){
        val disposable = dbProvider.getTestFoundList().subscribeBy {
            viewState.getFoundList(it)
        }
        compositeDisposable.add(disposable)
    }

    override fun destroyView(view: FoundListView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
