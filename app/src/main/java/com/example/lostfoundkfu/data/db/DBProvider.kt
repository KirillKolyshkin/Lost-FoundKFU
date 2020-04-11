package com.example.lostfoundkfu.data.db

import android.graphics.Bitmap
import com.example.lostfoundkfu.data.Items.BuildingWithoutFlag
import com.example.lostfoundkfu.data.Items.LostItem
import com.example.lostfoundkfu.data.user.User
import com.example.lostfoundkfu.data.user.UserResponse
import com.google.gson.Gson
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import kotlin.collections.ArrayList

class DBProvider {

    fun getBuildings(): Maybe<ArrayList<BuildingWithoutFlag>> =
        Maybe.create { emitter ->
            val arrayList = ArrayList<BuildingWithoutFlag>()
            for (e in 1..10) {
                arrayList.add(BuildingWithoutFlag("Двойка", null))
            }
            emitter.onSuccess(arrayList)
        }

    fun deleteItem(item: LostItem) {}

    fun getSupposedLostList(item: LostItem): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (e in 1..10) {
                list.add(LostItem("TetItem", "", ArrayList()))
            }
            emitter.onSuccess(list)
        }

    fun getSupposedFoundList(item: LostItem): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (e in 1..10) {
                list.add(LostItem("TetItem", "", ArrayList()))
            }
            emitter.onSuccess(list)
        }

    fun getMyLostList(userLink: String): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (i in 1..20) {
                list.add(LostItem("Пельмеши", "", listOf("2йка"), Calendar.getInstance().time))
            }
            emitter.onSuccess(list)
        }

    fun getMyFoundList(userLink: String): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (i in 1..20) {
                list.add(LostItem("Пельмеши", "", listOf("2йка"), Calendar.getInstance().time))
            }
            emitter.onSuccess(list)
        }

    fun getTestLostList(): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (i in 1..20) {
                list.add(LostItem("Пельмеши", "", listOf("2йка"), Calendar.getInstance().time))
            }
            emitter.onSuccess(list)
        }


    fun getTestFoundList(): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (i in 1..20) {
                list.add(
                    LostItem(
                        "Пельмеши",
                        "Потеряли пельмеши, что тут сказать",
                        listOf("2йка"),
                        Calendar.getInstance().time,
                        null,
                        null,
                        true
                    )
                )
            }
            emitter.onSuccess(list)
        }

    fun addObject(
        name: String,
        description: String,
        place: List<String>,
        date: Date,
        category: String?,
        image: Bitmap?,
        isFound: Boolean
    ) {

    }
}
