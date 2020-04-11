package com.example.lostfoundkfu.data.Items

import java.io.Serializable
import java.util.*

data class LostItem(
    val name: String = "",
    val description: String = "",
    val place: List<String>,
    val date: Date = Date(),
    val imageUrl: String? = null,
    val userLink: String? = null,
    val isFound: Boolean = false
) : Serializable

