package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "notes_table")
data class Note (
        val note: String,
        val created_at : Date,
        val updated_at : Date,
        val bookId: Int

) {
        @PrimaryKey(autoGenerate=true)
        var id : Long = 0
}


