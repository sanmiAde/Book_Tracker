package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.sanmiaderibigbe.booktracker.model.IBook
import java.util.*

@Entity(tableName = "books_table", indices = [Index(value =["name"], unique = true), Index(value = ["readYear"])])
/***
 *
 */
data class Book(
        override val name: String,
        override val author: String,
        override val genre: String,
        override val numberOfPages: Int,
        override val rating: Double?,
        val readYear: String,
        val goalId : Long? =null,
        val state: BookState?,
        val created_at : Date,
        val updated_at : Date ) : IBook {
        @PrimaryKey(autoGenerate = true)
        var id : Long= 0
}
