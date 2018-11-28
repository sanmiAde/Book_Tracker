package com.sanmiaderibigbe.booktracker.data.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

object DateConverter {
    @TypeConverter
    @JvmStatic
    fun fromTimeStamp(value: Long?): Date? {
        return if(value == null) null else Date(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long?{
        return date?.time
    }
}