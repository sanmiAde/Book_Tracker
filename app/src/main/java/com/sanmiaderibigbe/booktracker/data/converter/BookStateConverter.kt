package com.sanmiaderibigbe.booktracker.data.converter

import android.arch.persistence.room.TypeConverter
import com.sanmiaderibigbe.booktracker.data.model.BookState

object BookStateConverter{
    @TypeConverter
    @JvmStatic
    fun fromEnumToInt(value: BookState?): Int? {
      return when(value) {
          BookState.READING -> 0
           BookState.TO_READ -> 1
           BookState.READ -> 2

          else -> null
      }


    }

    @TypeConverter
    @JvmStatic
    fun fromIntToEnum(stateInt: Int?): BookState?{
        return BookState.values()[stateInt!!]
    }
}