package com.sanmiaderibigbe.booktracker.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import com.sanmiaderibigbe.booktracker.data.model.BookWithNotes
import com.sanmiaderibigbe.booktracker.data.model.GoalWithBooks

@Dao
interface BookDao {

    @Query("SELECT * FROM books_table ORDER BY id")
    fun getBooks(): List<Book>


    @Query("SELECT * FROM books_table WHERE id =:bookid ")
    fun getBook(bookid: Long): Book

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: Book) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(books: Book)

    @Delete
    fun deleteOrder(quotes: Book)

    @Query("SELECT * FROM books_table WHERE state =:bookState ")
    fun getBooksByBookState(bookState: BookState): List<Book>

    @Query("SELECT * FROM books_table WHERE id =:bookid ")
    @Transaction
    fun getBooksWithNotes(bookid: Long) : BookWithNotes

    @Query("SELECT * FROM books_table")
    @Transaction
    fun getAllBooksWithNotes() :List<BookWithNotes>


}