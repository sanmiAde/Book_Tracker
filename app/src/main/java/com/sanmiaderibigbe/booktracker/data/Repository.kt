package com.sanmiaderibigbe.booktracker.data

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import android.util.Log
import com.sanmiaderibigbe.booktracker.data.database.AppDatabase
import com.sanmiaderibigbe.booktracker.data.database.dao.BookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.CacheBookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.GoalDao
import com.sanmiaderibigbe.booktracker.data.database.dao.NoteDao
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import com.sanmiaderibigbe.booktracker.data.model.CacheBook
import com.sanmiaderibigbe.booktracker.data.model.Goal
import com.sanmiaderibigbe.booktracker.network.GoogleBooksInterface
import com.sanmiaderibigbe.booktracker.network.NetWorkState
import com.sanmiaderibigbe.booktracker.network.RetrofitInstance
import com.sanmiaderibigbe.booktracker.network.model.Item
import com.sanmiaderibigbe.booktracker.network.model.VolumeInfo
import com.sanmiaderibigbe.booktracker.utils.AppExecutors
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(val application: Application, private val appExecutors: AppExecutors) {

    private val bookDao: BookDao
    private val goalDao: GoalDao
    private val noteDao: NoteDao
    private val cacheBookDao: CacheBookDao
    private val networkState: MutableLiveData<NetWorkState> = MutableLiveData()
    private val TAG = Repository::class.simpleName

    init {
        val db = AppDatabase.getDatabase(application, false)
        bookDao = db.bookDao()
        goalDao = db.goalDao()
        noteDao = db.noteDao()
        cacheBookDao = db.cacheDao()

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

    fun getBookDataOnline(name: String) {
        initApiCall(name)
//        return when {
//            getCountOfBookInCacheByName(name) == 0 -> {
//                initApiCall(name)
//                getBookFromCache(name)
//            }
//            else -> getBookFromCache(name)
//        }
    }


    private fun getCountOfBookInCacheByName(name: String): Int {
//        return GetCountOfBookFromCacheByName(cacheBookDao).doInBackground(name)
        var numberOfBook: Int = Int.MAX_VALUE
        doAsync {
            numberOfBook = cacheBookDao.getCountOfBookInCacheByName(name)
        }
        return numberOfBook
    }

    // store in db first Db frit
    private fun getBookFromCache(name: String): CacheBook {
        return GetBookFromCache(cacheBookDao).doInBackground(name)
    }

    private fun saveCacheBook(volumeInfo: VolumeInfo) {
        val book = CacheBook(0, volumeInfo.title!!, volumeInfo.authors!![0], volumeInfo.categories!![0], volumeInfo.pageCount!!, volumeInfo.averageRating!!)
        appExecutors.diskIO().execute { cacheBookDao.insert(book) }
    }

    fun saveGoal(goal: Goal) {
        doAsync {
            goalDao.insert(goal)
        }
    }

    fun getGoal(year: String): Goal? {
        var currentGoal: Goal? = null
        doAsync {
            currentGoal = goalDao.getGoalByYear(year)

        }

        return currentGoal
    }


    class GetBookFromCache(private val dao: CacheBookDao) : AsyncTask<String, Void, CacheBook>() {
        public override fun doInBackground(vararg p0: String?): CacheBook {
            return dao.getBookFromCacheByName(p0[0]!!)
        }

    }

    class GetCountOfBookFromCacheByName(private val dao: CacheBookDao) : AsyncTask<String, Void, Int>() {
        public override fun doInBackground(vararg p0: String?): Int {
            return dao.getCountOfBookInCacheByName(p0[0]!!)
        }

    }

    private fun initApiCall(bookName: String): MutableLiveData<List<VolumeInfo>> {
        networkState.value = NetWorkState.NotLoaded

        val service: GoogleBooksInterface = RetrofitInstance.initRetrofitInstance()
        val call: Call<Item> = service.getBooks(bookName)
        val volumeInfoLive: MutableLiveData<List<VolumeInfo>> = MutableLiveData()

        networkCall(call)

        return volumeInfoLive
    }

    private fun networkCall(call: Call<Item>) {
        networkState.value = NetWorkState.Loading
        call.enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e(TAG, t.message)
                networkState.value = NetWorkState.Error(t.message)
            }

            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                when {
                    response.code() == 200 -> when {
                        response.body()?.volumeInfo != null -> {
                            networkState.value = NetWorkState.Success
//                           saveCacheBook(  response.body()!!.volumeInfo!![0])


                        }
                        else -> {
                            val bookName: String = response.raw().request().url().queryParameter("q")!!
                            networkState.value = NetWorkState.Error("$bookName not found")
                        }
                    }
                }
            }

        })
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