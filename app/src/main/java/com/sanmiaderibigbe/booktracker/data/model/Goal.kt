package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "goals_table", indices = [Index(value = ["year", "isActive"], unique = true)])
class Goal(
        val numberOfBooksToRead : Int = 0,
        val year: String,
        val isActive: Boolean,
        val created_at : Date,
        val updated_at : Date
//Add primary key on year

        ) {
        @PrimaryKey(autoGenerate = true)
        var id : Long = 0
}