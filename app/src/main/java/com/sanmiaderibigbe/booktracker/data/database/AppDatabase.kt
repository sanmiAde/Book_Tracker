package com.sanmiaderibigbe.booktracker.data.database

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.sanmiaderibigbe.booktracker.data.converter.BookStateConverter
import com.sanmiaderibigbe.booktracker.data.converter.DateConverter
import com.sanmiaderibigbe.booktracker.data.database.dao.BookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.CacheBookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.GoalDao
import com.sanmiaderibigbe.booktracker.data.database.dao.NoteDao
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.CacheBook
import com.sanmiaderibigbe.booktracker.data.model.Goal
import com.sanmiaderibigbe.booktracker.data.model.Note

@TypeConverters(DateConverter::class, BookStateConverter::class)
@android.arch.persistence.room.Database(entities = [Book::class, Goal::class, Note::class, CacheBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao() : BookDao
    abstract fun noteDao(): NoteDao
    abstract fun goalDao(): GoalDao
    abstract fun cacheDao(): CacheBookDao

    //Todo export schema
    companion object {
        private var  sBuilder : RoomDatabase.Builder<AppDatabase>? = null
        private const val  DB_NAME ="database"


//        private fun createBook(name: Int, state: BookState): Book = Book(name = "ssa$name",author = "sds",genre= "dfd", numberOfPages = 12, rating= 4.0, readYear = "2018",state= state,created_at =  Date(), updated_at =  Date())
//
//       private fun createNote(name: Int, bookId: Int) : Note = Note("sdskndsk$name", Date(), Date(), bookId)
//
//       private fun createGoal(year: String): Goal = Goal(0,0, year,Date(), Date())

        /***
         * Creates database
         * @memoryOnly: create an in memory database sBuilder if true.
         */
        @Synchronized
        fun getDatabase(context: Context, memoryOnly: Boolean): AppDatabase {
            when (sBuilder) {
                null -> sBuilder = when {
                    memoryOnly -> Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java)
                    else -> Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                }
            }
            return  sBuilder!!.build()


        }
    }


}


//.addCallback(object  : RoomDatabase.Callback(){
//    override fun onCreate(db: SupportSQLiteDatabase) {
//        super.onCreate(db)
//
//        val executor = AppExecutors.getExecutor()
//        val callbackDb = AppDatabase.getDatabase(context,false)
//        val bookDao = callbackDb.bookDao()
//        val noteDao = callbackDb.noteDao()
//        val goalDao  = callbackDb.goalDao()
//
//        (1..20).forEach { i ->
//            executor.diskIO().execute {bookDao.insert(AppDatabase.createBook(i, BookState.READING))}
//        }
//
//        (20..40).forEach { i ->
//            executor.diskIO().execute {bookDao.insert(AppDatabase.createBook(i, BookState.READ))}
//        }
//
//        (40..60).forEach { i ->
//            executor.diskIO().execute {bookDao.insert(AppDatabase.createBook(i, BookState.TO_READ))}
//        }
//
//    }
//
//})
//}
//

