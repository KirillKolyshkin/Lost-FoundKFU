package com.example.lostfoundkfu.features.createlostobject

import android.content.Context
import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.db.DBProvider
import com.example.lostfoundkfu.features.foundlist.FoundListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*

@InjectViewState
class CreateLostObjectPresenter(private val dbProvider: DBProvider) :
    MvpPresenter<CreateLostObjectView>() {
    private val compositeDisposable = CompositeDisposable()

    fun getBuildings(context: Context) {
        val disposable = dbProvider.getBuildings(context).subscribeBy {
            viewState.getBuildings(it)
        }
        compositeDisposable.add(disposable)
    }

    fun addObject(
        name: String,
        description: String,
        place: List<String>,
        date: Date,
        category: String?,
        image: Bitmap?,
        isFound: Boolean
    ) = dbProvider.addObject(name, description, place, date, category, image, isFound)

    override fun destroyView(view: CreateLostObjectView?) {
        super.destroyView(view)
        compositeDisposable.clear()
    }
}
