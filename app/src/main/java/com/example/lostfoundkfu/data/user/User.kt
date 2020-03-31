package com.example.lostfoundkfu.data.user

data class User(
    val first_name: String,
    val last_name: String,
    val screen_name: String,
    val sex: Int,
    val relation: Int,
    val bdate: String,
    val bdate_visibility: Int,
    val home_town: String,
    val country: Country,
    val city: City,
    val status: String,
    val phone: String
)

data class City(
    val id: String,
    val title: String
)

data class Country(
    val id: String,
    val title: String
)

data class UserResponse(val response: User)
