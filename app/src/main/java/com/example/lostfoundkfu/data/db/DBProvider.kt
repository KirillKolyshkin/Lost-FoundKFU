package com.example.lostfoundkfu.data.db

import com.example.lostfoundkfu.data.Items.LostItem
import io.reactivex.Maybe
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

}