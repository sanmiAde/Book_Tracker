package com.sanmiaderibigbe.booktracker.ui.ui.toread

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import com.sanmiaderibigbe.booktracker.ui.BaseViewModel

class ToReadViewModel(application: Application) : BaseViewModel(application) {
    override fun getBookList(): LiveData<List<Book>> {
        return  repository.getBookByState(BookState.READ)
    }
    // TODO: Implement the ViewModel


}
