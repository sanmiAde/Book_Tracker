package com.sanmiaderibigbe.booktracker.ui.ui.add

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.sanmiaderibigbe.booktracker.data.Repository
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.Goal

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository = Repository.getRepository(application)


    fun saveBook(book : Book){
        repository.saveBook(book)
    }

    fun updateBook(book: Book) {
        repository.updateBook(book)
    }


    fun getGoal(year: String): Goal? {
        return repository.getGoal(year)
    }
}
