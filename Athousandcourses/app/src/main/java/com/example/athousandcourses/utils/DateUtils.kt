package com.example.athousandcourses.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    //private val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.RU) тут RU убрали
    private val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))

    fun formatStartDate(dateString: String): String {
        val date = LocalDate.parse(dateString)
        return date.format(formatter)
    }
}