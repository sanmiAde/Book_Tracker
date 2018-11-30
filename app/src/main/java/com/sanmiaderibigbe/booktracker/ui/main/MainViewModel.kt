package com.sanmiaderibigbe.booktracker.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sanmiaderibigbe.booktracker.data.database.AppDatabase
import com.sanmiaderibigbe.booktracker.data.model.Book

class MainViewModel(application: Application) : AndroidViewModel(application) {

   private val db = AppDatabase.getDatabase(application,false)
   private val bookDao = db.bookDao()

    fun getBookList() : LiveData<List<Book>> = bookDao.getBooks()
}