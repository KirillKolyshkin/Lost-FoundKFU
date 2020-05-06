package com.example.lostfoundkfu.data.db

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import androidx.constraintlayout.widget.Constraints
import com.example.lostfoundkfu.R
import com.example.lostfoundkfu.data.Items.BuildingWithoutFlag
import com.example.lostfoundkfu.data.Items.LostItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.Maybe
import io.reactivex.rxkotlin.subscribeBy
import java.io.ByteArrayOutputStream
import java.util.*


class DBProvider(
    private val database: FirebaseFirestore,
    private val sharedPreferences: SharedPreferences,
    private val firebaseStorage: FirebaseStorage
) {

    fun addImageToStorage(name: String, bitmap: Bitmap, path: String): Maybe<String> {
        var storageRef = firebaseStorage.reference
        var imagesRef: StorageReference? = storageRef
            .child("images")
            .child(path)//"userImages"
            .child("$name.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos)
        val data = baos.toByteArray()
        return Maybe.create { emitter ->
            var uploadTask = imagesRef?.putBytes(data)?.addOnSuccessListener {
                imagesRef.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        emitter
                            .onSuccess(downloadUri.toString())
                    }
                }
            }
        }
    }

    fun getBuildings(context: Context): Maybe<ArrayList<BuildingWithoutFlag>> =
        Maybe.create { emitter ->
            val arrayList = ArrayList<BuildingWithoutFlag>()
            arrayList.add(BuildingWithoutFlag("Учебное здание №1", context.getDrawable(R.mipmap.ic_first_building)))
            arrayList.add(BuildingWithoutFlag("Учебное здание №2", context.getDrawable(R.mipmap.ic_second_building)))
            arrayList.add(BuildingWithoutFlag("Учебное здание №12", context.getDrawable( R.mipmap.ic_twelve_building)))
            arrayList.add(BuildingWithoutFlag("Учебное здание №19", context.getDrawable( R.mipmap.ic_nineteen_building)))
            arrayList.add(BuildingWithoutFlag("Общежитие №8", context.getDrawable( R.mipmap.ic_eight_ob)))
            arrayList.add(BuildingWithoutFlag("Общежитие №9", context.getDrawable( R.mipmap.ic_nine_ob)))
            arrayList.add(BuildingWithoutFlag("Деревня Универсиады", context.getDrawable( R.mipmap.ic_university_village)))
            arrayList.add(BuildingWithoutFlag("Уникс", context.getDrawable( R.mipmap.ic_uniks)))
            emitter.onSuccess(arrayList)
        }


    fun getSupposedLostList(item: LostItem): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            database
                .collection("items")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = ArrayList<LostItem>()
                    val lowBound = item.date.time - (60000 * 60 * 24 * 7)
                    val highBound = item.date.time + (60000 * 60 * 24 * 7)
                    for (document in documentSnapshot) {
                        val supposedItem = document.toObject(LostItem::class.java)
                        if (!supposedItem.found
                            && supposedItem.category == item.category
                            && supposedItem.date.time in lowBound..highBound
                        )
                            list.add(supposedItem)
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    Log.d(Constraints.TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }

    fun getSupposedFoundList(item: LostItem): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            database
                .collection("items")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = ArrayList<LostItem>()
                    val lowBound = item.date.time - (60000 * 60 * 24 * 7)
                    val highBound = item.date.time + (60000 * 60 * 24 * 7)
                    for (document in documentSnapshot) {
                        val supposedItem = document.toObject(LostItem::class.java)
                        if (supposedItem.found
                            && supposedItem.category == item.category
                            && supposedItem.date.time in lowBound..highBound
                        )
                            list.add(supposedItem)
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    Log.d(Constraints.TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }

    fun getTestLostList(): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            database
                .collection("items")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = ArrayList<LostItem>()
                    for (document in documentSnapshot) {
                        val supposedItem = document.toObject(LostItem::class.java)
                        if (!supposedItem.found)
                            list.add(supposedItem)
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    Log.d(Constraints.TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }


    fun getTestFoundList(): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            database
                .collection("items")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = ArrayList<LostItem>()
                    for (document in documentSnapshot) {
                        val supposedItem = document.toObject(LostItem::class.java)
                        if (supposedItem.found)
                            list.add(supposedItem)
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    Log.d(Constraints.TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }

    fun getMyLostList(userLink: String): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            database
                .collection("items")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = ArrayList<LostItem>()
                    for (document in documentSnapshot) {
                        val supposedItem = document.toObject(LostItem::class.java)
                        if (supposedItem.userLink == userLink && !supposedItem.found)
                            list.add(supposedItem)
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    Log.d(Constraints.TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }

    fun getMyFoundList(userLink: String): Maybe<ArrayList<LostItem>> =
        Maybe.create { emitter ->
            database
                .collection("items")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    val list = ArrayList<LostItem>()
                    for (document in documentSnapshot) {
                        val supposedItem = document.toObject(LostItem::class.java)
                        if (supposedItem.userLink == userLink && supposedItem.found)
                            list.add(supposedItem)
                    }
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    Log.d(Constraints.TAG, "Error getting documents: ", exception)
                    emitter.onError(exception)
                }
        }

    fun deleteItem(item: LostItem) {
        database
            .collection("items")
            .document(item.userLink + item.name)
            .delete()
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
        val curUser = UserProvider.curUser!!
        if (image != null) {
            val disposable =
                addImageToStorage(curUser.screen_name, image, "ItemPhoto").subscribeBy {
                    val item =
                        LostItem(
                            name,
                            description,
                            place,
                            date,
                            it,
                            curUser.screen_name,
                            isFound,
                            category
                        )
                    database
                        .collection("items")
                        .document(curUser.screen_name + name)
                        .set(item)
                }
        }
    }
}
