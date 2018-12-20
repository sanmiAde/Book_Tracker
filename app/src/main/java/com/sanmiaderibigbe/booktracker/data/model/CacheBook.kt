package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.sanmiaderibigbe.booktracker.model.IBook

@Entity(tableName = "cache_books_table")
data class CacheBook(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        override var name: String,
        override val author: String,
        override val genre: String,
        override val numberOfPages: Int,
        override val rating: Double?

) : IBook