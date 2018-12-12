package com.sanmiaderibigbe.booktracker.ui.ui.add

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel
import com.sanmiaderibigbe.booktracker.data.Repository
import com.sanmiaderibigbe.booktracker.data.model.Book

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getRepository(application)
    private lateinit var mViewModel: AddViewModel

    fun saveBook(book : Book){
        repository.saveBook(book)
    }
}
