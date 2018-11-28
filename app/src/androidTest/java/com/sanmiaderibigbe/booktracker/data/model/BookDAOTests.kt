package com.sanmiaderibigbe.booktracker.data.model

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sanmiaderibigbe.booktracker.data.database.dao.BookDao
import com.sanmiaderibigbe.booktracker.data.database.AppDatabase

import junit.framework.TestCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class BookDAOTests {
    var  mDb: AppDatabase? = null
    var  dao: BookDao? = null
//    val book: Book = Book()

    @Before
    fun setUp() {
        mDb = AppDatabase.getDatabase(InstrumentationRegistry.getTargetContext(), true)
        dao  = mDb!!.bookDao()
    }

    @Test
    @Throws(Exception::class)
    fun insertTest() {
        assertQuotes( 0)
        val book = createBook(334)
        dao!!.insert(book)
        assertQuotes( 1)
    }

    @Test
    @Throws(Exception::class)
    fun getBookTest(){

       var id = createManyBooks()
        assertQuotes(20)

        val gottenBook = dao!!.getBook(id)
        assert(gottenBook.id == id)
    }

    @Test
    @Throws(Exception::class)
    fun deleteBookTest(){

       var id = createManyBooks()
        assertQuotes(20)
        val gottenBook = dao!!.getBook(id)
        dao!!.deleteOrder(gottenBook)

        assertQuotes(19)
        Assert.assertNull(dao!!.getBook(id))
    }

    fun createManyBooks(): Long {
        var id: Long = Long.MIN_VALUE
        for (i in 1..20) {
            id = dao!!.insert(createBook(name = i))
        }
        return id
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


    @Test
    @Throws(Exception::class)
    fun updateTest() {
        assertQuotes(0)
        val book = createBook(334)
        dao!!.insert(book)
        val updatebook = book.copy(name = "345")
        dao!!.update(updatebook)
        val updatedBook = dao!!.getBook(1)
        assert(updatebook.name == updatedBook.name)
    }

    @Test
    @Throws(Exception::class)
    fun getReadBookTest(){

        createManyBooks()
        createManyReadBooks()
        createManyToReadBooks()
        assertQuotes(62)

        val toReadList = dao!!.getBooksByBookState(BookState.TO_READ)
        val firstBook = toReadList[0]
        Assert.assertEquals(firstBook.state, BookState.TO_READ)

    }


    private fun assertQuotes( size: Int) {
        val result = dao!!.getBooks()

        Assert.assertNotNull(result)
        TestCase.assertEquals(size, result.size)

    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        mDb!!.close()
    }
}