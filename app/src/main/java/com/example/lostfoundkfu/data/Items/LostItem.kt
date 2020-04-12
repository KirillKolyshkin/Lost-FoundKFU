package com.example.lostfoundkfu.data.Items

import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class LostItem(
    val name: String = "",
    val description: String = "",
    val place: List<String> = ArrayList(),
    val date: Date = Date(),
    val imageUrl: String? = null,
    val userLink: String? = null,
    val isFound: Boolean = false,
    val category: String? = null
) : Serializable

