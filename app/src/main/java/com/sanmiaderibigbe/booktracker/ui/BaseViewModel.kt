package com.sanmiaderibigbe.booktracker.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sanmiaderibigbe.booktracker.data.Repository
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import java.util.*

open abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val repository: Repository = Repository.getRepository(application)
    abstract fun getBookList() : LiveData<List<Book>>

    fun moveToReading(book: Book) {
        val updatedBook = book.copy(state = BookState.READING, updated_at = Date(), created_at = Date(), end_date = null)
        repository.updateBook(updatedBook)
    }

    fun moveToReadLater(book: Book){
        val updatedBook = book.copy(state = BookState.TO_READ, updated_at = Date(), end_date = null)
        repository.updateBook(updatedBook)
    }

    fun moveToRead(book: Book) {
        val updatedBook = book.copy(state = BookState.READ, updated_at = Date(), end_date = Date())
        repository.updateBook(updatedBook)
    }

    fun deleteBook(book: Book){
        repository.deleteBook(book)
    }

}