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
}