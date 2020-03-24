package com.example.lostfoundkfu.data.Items

import java.util.*

data class LostItem(
    val name: String = "",
    val place: String = "",
    val date: Date = Date(),
    val imageUrl: String? = null
)