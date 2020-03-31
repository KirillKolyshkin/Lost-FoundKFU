package com.example.lostfoundkfu.data.db

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

    fun getTestLostList(): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (i in 1..20) {
                list.add(LostItem("Пельмеши", "2йка", Calendar.getInstance().time))
            }
            emitter.onSuccess(list)
        }


    fun getTestFoundList(): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            val list = ArrayList<LostItem>()
            for (i in 1..20) {
                list.add(LostItem("Пельмеши", "2йка", Calendar.getInstance().time, null, true))
            }
            emitter.onSuccess(list)
        }
}
