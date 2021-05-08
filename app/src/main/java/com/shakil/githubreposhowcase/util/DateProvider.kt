package com.shakil.githubreposhowcase.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateProvider @Inject constructor() {

    fun provideFormater(): SimpleDateFormat  {
        val pattern = "yyyy-MM-dd"
        return SimpleDateFormat(pattern, Locale.getDefault())
    }

    fun provideDate(): Date {
        return Date();
    }

    fun getCurrentTimeStamp(): String {
        val formatter = provideFormater()
        return formatter.format(provideDate())
    }
}