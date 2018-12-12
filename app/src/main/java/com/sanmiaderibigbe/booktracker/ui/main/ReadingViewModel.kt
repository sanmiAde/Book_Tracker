package com.sanmiaderibigbe.booktracker.ui.main

import android.app.Application
import android.arch.lifecycle.LiveData
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState

import com.sanmiaderibigbe.booktracker.ui.BaseViewModel

class ReadingViewModel(application: Application): BaseViewModel(application) {
    override fun getBookList(): LiveData<List<Book>> {
        return  repository.getBookByState(BookState.READING)
    }




}