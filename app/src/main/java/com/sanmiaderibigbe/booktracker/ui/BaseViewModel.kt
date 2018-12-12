package com.sanmiaderibigbe.booktracker.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sanmiaderibigbe.booktracker.data.Repository
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState

open abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val repository: Repository = Repository.getRepository(application)
    abstract fun getBookList() : LiveData<List<Book>>

    fun moveToReading(book: Book) {
        val updatedBook = book.copy(state = BookState.READING)
        repository.updateBook(updatedBook)
    }

    fun moveToReadLater(book: Book){
        val updatedBook = book.copy(state = BookState.TO_READ)
        repository.updateBook(updatedBook)
    }

    fun moveToRead(book: Book) {
        val updatedBook = book.copy(state = BookState.READ)
        repository.updateBook(updatedBook)
    }

    fun deleteBook(book: Book){
        repository.deleteBook(book)
    }

}