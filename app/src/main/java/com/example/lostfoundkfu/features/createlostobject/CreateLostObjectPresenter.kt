package com.example.lostfoundkfu.features.createlostobject

import android.graphics.Bitmap
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lostfoundkfu.data.db.DBProvider
import io.reactivex.rxkotlin.subscribeBy
import java.util.*

@InjectViewState
class CreateLostObjectPresenter(private val dbProvider: DBProvider): MvpPresenter<CreateLostObjectView>() {

    fun getBuildings(){
        val disposable = dbProvider.getBuildings().subscribeBy {
            viewState.getBuildings(it)
        }
    }

    fun addObject(name: String,
                  place: List<String>,
                  date: Date,
                  category: String?,
                  image: Bitmap?,
                  isFound: Boolean) = dbProvider.addObject(name, place, date, category, image, isFound)
}
