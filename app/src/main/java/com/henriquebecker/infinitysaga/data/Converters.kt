package com.henriquebecker.infinitysaga.data

import java.util.*
import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar?): Long? {
        calendar?: return null
        return calendar.timeInMillis
    }
    @TypeConverter fun datestampToCalendar(value: Long?): Calendar? {
        value?: return null
        return Calendar.getInstance().apply { timeInMillis = value }
    }
}