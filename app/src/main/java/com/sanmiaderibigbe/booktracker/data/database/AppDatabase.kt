package com.sanmiaderibigbe.booktracker.data.database

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.sanmiaderibigbe.booktracker.data.converter.BookStateConverter
import com.sanmiaderibigbe.booktracker.data.converter.DateConverter
import com.sanmiaderibigbe.booktracker.data.database.dao.BookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.NoteDao
import com.sanmiaderibigbe.booktracker.data.database.dao.GoalDao
import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.Goal
import com.sanmiaderibigbe.booktracker.data.model.Note

@android.arch.persistence.room.Database(entities = [Book::class, Goal::class, Note::class], version = 1)
@TypeConverters(DateConverter::class, BookStateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao() : BookDao
    abstract fun noteDao(): NoteDao
    abstract fun goalDao(): GoalDao

    //Todo export schema
    companion object {
        private var  sBuilder : RoomDatabase.Builder<AppDatabase>? = null
        private const val  DB_NAME ="database"

        /***
         * Creates database
         * @memoryOnly: create an in memory database sBuilder if true.
         */
        @Synchronized
        fun getDatabase(context: Context, memoryOnly: Boolean): AppDatabase {
            when (sBuilder) {
                null -> sBuilder = when {
                    memoryOnly -> Room.inMemoryDatabaseBuilder(context.applicationContext, AppDatabase::class.java )
                    else -> Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                }
            }
            return  sBuilder!!.build()
        }
    }
}