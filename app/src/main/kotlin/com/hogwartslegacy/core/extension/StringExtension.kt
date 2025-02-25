package com.hogwartslegacy.core.extension

import java.text.SimpleDateFormat
import java.util.Locale

fun String.dateShortMonthYear(): String {
    val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: return this)
    } catch (e: Exception) {
        this
    }
}