package com.sanmiaderibigbe.booktracker.data.model

import android.support.test.InstrumentationRegistry
import com.sanmiaderibigbe.booktracker.data.database.AppDatabase
import com.sanmiaderibigbe.booktracker.data.database.dao.BookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.GoalDao
import org.junit.Assert
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class GoalWithBooksTest {
    var  mDb: AppDatabase? = null
    var  dao: BookDao? = null
    var goalDao: GoalDao? = null
//    val book: Book = Book()

    @Before
    fun setUp() {
        mDb = AppDatabase.getDatabase(InstrumentationRegistry.getTargetContext(), true)
        dao  = mDb!!.bookDao()
        goalDao = mDb!!.goalDao()
    }

    @Test
    @Throws(Exception::class)
    fun getGoalsWithBooks(){
        createManyReadBooks()
        createManyToReadBooks()
        goalDao!!.insert(createGoal("2018"))
        goalDao!!.insert(createGoal("2019"))
        dao!!.insert(completeBook(12, 1))
        dao!!.insert(completeBook(124, 1))
        dao!!.insert(completeBook(1266, 2))
        dao!!.insert(completeBook(1234, 2))

        val goalswiithbook = goalDao!!.getGoalWithBooks("2018")
        val goals = goalDao!!.getGoalsWithBooks()

        assertEquals(goalswiithbook.books!!.size, 2)
        Assert.assertEquals(goalswiithbook.goal!!.year, "2018")
        assertEquals(goals.size, 2)

    }


    fun createManyToReadBooks(): Long {
        var id: Long = Long.MIN_VALUE
        for (i in 21..41) {
            id = dao!!.insert(createBookToRead(name = i))
        }
        return id
    }

    fun createManyReadBooks(): Long {
        var id: Long = Long.MIN_VALUE
        for (i in 42..62) {
            id = dao!!.insert(createBookRead(name = i))
        }
        return id
    }


}