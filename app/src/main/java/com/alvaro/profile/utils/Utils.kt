package com.alvaro.profile.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.parseDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).parse(this)

    return formatter.format(date ?: "")
}

fun String.getGender(): String {
    return when (this) {
        "miss" -> "Female"
        "ms" -> "Female"
        "mrs" -> "Female"
        "mr" -> "Male"
        else -> "Unknown"
    }
}