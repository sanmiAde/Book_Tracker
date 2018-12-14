package com.sanmiaderibigbe.booktracker.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.sanmiaderibigbe.booktracker.model.IBook
import java.io.Serializable
import java.util.*

@Entity(tableName = "books_table", indices = [Index(value =["name"], unique = true), Index(value = ["readYear"])])
/***
 *
 */
data class Book(
        @PrimaryKey(autoGenerate = true)
        var id : Long= 0,
        override var name: String,
        override val author: String,
        override val genre: String,
        //todo add current page
        val currentPage: Int,
        override val numberOfPages: Int,
        override val rating: Double?,
        val readYear: String,
        val goalId : Long? =null,
        //val currentPage : Int,
        val state: BookState?,
        val created_at : Date?,
        val end_date : Date?,
        val updated_at: Date) : IBook, Serializable
