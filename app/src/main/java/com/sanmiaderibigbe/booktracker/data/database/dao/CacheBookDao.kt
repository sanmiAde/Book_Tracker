package com.sanmiaderibigbe.booktracker.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.sanmiaderibigbe.booktracker.data.model.CacheBook

@Dao
interface CacheBookDao {

    @Query("SELECT * FROM books_table ORDER BY id")
    fun getBooks(): LiveData<List<CacheBook>>

    @Query("SELECT * FROM books_table WHERE id =:bookid ")
    fun getBook(bookid: Long): CacheBook

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: CacheBook): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(books: CacheBook)

    @Delete
    fun delete(quotes: CacheBook)

    @Query("SELECT * FROM  cache_books_table WHERE name=:bookName")
    fun getBookFromCacheByName(bookName: String): CacheBook

    @Query("SELECT COUNT(*) FROM  cache_books_table WHERE name=:bookName")
    fun getCountOfBookInCacheByName(bookName: String): Int
}