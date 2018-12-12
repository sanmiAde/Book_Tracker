package com.sanmiaderibigbe.booktracker.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.sanmiaderibigbe.booktracker.data.database.AppDatabase
import com.sanmiaderibigbe.booktracker.data.database.dao.BookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.GoalDao
import com.sanmiaderibigbe.booktracker.data.database.dao.NoteDao
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import com.sanmiaderibigbe.booktracker.utils.AppExecutors

class Repository(application: Application, private val appExecutors: AppExecutors) {

    private val bookDao: BookDao
    private val goalDao: GoalDao
    private val noteDao: NoteDao

    init {
        val db = AppDatabase.getDatabase(application, false)
        bookDao = db.bookDao()
        goalDao = db.goalDao()
        noteDao = db.noteDao()
    }

    fun getBookByState(state: BookState) : LiveData<List<Book>>{
        return GetBookByState(bookDao, state).doInBackground()
    }

    fun updateBook(book: Book) {
       appExecutors.diskIO().execute{
           bookDao.update(book)
       }
    }

    fun saveBook(book: Book) {
        appExecutors.diskIO().execute {
            bookDao.insert(book)
        }
    }

    fun deleteBook(book: Book) {
        appExecutors.diskIO().execute {
            bookDao.delete(book)
        }
    }

    class GetBookByState(private val dao: BookDao, private val state: BookState) : AsyncTask<Void, Void, LiveData<List<Book>>>() {
        public override fun doInBackground(vararg p0: Void?): LiveData<List<Book>> {
            return dao.getBooksByBookState(state)
        }

    }

    class DeleteBook(private val dao: BookDao) : AsyncTask<Book, Void,Unit>() {
        public override fun doInBackground(vararg books: Book?): Unit {
            return dao.update(books[0]!!)
        }


    }

    companion object {
        private var instance: Repository? = null

        @Synchronized
        fun getRepository(application: Application): Repository {
            if (instance == null) {
                val appExecutors = AppExecutors.getExecutor()
                instance = Repository(application, appExecutors)
            }
            return instance!!
        }
    }
}