package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "goals_table", indices = [Index(value =["year"], unique = true)])
class Goal(
        val numberOfBooksToRead : Int = 0,
        val numberOfBooksRead : Int = 0,
        val year: String,
        val created_at : Date,
        val updated_at : Date

        ) {
        @PrimaryKey(autoGenerate = true)
        var id : Long = 0
}