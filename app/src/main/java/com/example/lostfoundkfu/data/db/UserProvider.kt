package com.example.lostfoundkfu.data.db

import com.example.lostfoundkfu.data.user.User
import com.example.lostfoundkfu.data.user.UserResponse
import com.google.gson.Gson
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList

class UserProvider {

    fun setUser() {
        val vkRequest = VKRequest("account.getProfileInfo")
        vkRequest.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                val user = Gson().fromJson(response?.responseString, UserResponse::class.java)
                curUser = user.response
                //val disposable = register().subscribeBy(onSuccess = {}, onError = {}) TODO add user to Firebase
            }
        }
        )
        setUserPic()
    }

    fun setUserPic() {
        val yourRequest = VKApi.users()
            .get(
                VKParameters.from(
                    VKApiConst.USER_IDS,
                    curUser?.screen_name,
                    VKApiConst.FIELDS,
                    "photo_400_orig"
                )
            )

        yourRequest.executeSyncWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                val usersArray = response?.parsedModel as VKList<VKApiUser>
                for (userFull in usersArray) {
                    curUserPic = userFull.photo_400_orig
                }
            }
        })
    }

    companion object {
        var curUser: User? = null
        var curUserPic: String? = null
    }
}
