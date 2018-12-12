package com.sanmiaderibigbe.booktracker.ui.read

import android.app.Application
import android.arch.lifecycle.LiveData
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import com.sanmiaderibigbe.booktracker.ui.BaseViewModel

class ReadViewModel(application: Application) : BaseViewModel(application) {
    override fun getBookList(): LiveData<List<Book>> {
       return  repository.getBookByState(BookState.READ)
    }

}
