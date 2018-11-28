package com.sanmiaderibigbe.booktracker.data.model

import android.support.test.InstrumentationRegistry
import com.sanmiaderibigbe.booktracker.data.database.AppDatabase
import com.sanmiaderibigbe.booktracker.data.database.dao.BookDao
import com.sanmiaderibigbe.booktracker.data.database.dao.NoteDao
import org.junit.Before
import org.junit.Test

class BookWithNotesTest {
    var  mDb: AppDatabase? = null
    var  dao: BookDao? = null
    var notesDao: NoteDao? = null
//    val book: Book = Book()

    @Before
    fun setUp() {
        mDb = AppDatabase.getDatabase(InstrumentationRegistry.getTargetContext(), true)
        dao  = mDb!!.bookDao()
        notesDao = mDb!!.noteDao()
    }




    @Test
    fun saveNotes(){
        for (i in 1..10){
            dao!!.insert(createBook(i))
        }

        for (i in 1..10){
            for(j in 1..10){
                notesDao!!.insert(createNote(j,i))
            }
        }
        assert(dao!!.getBooks().size == 10)
    }

    @Test
    @Throws(Exception::class)
    fun  getBooksWithNotesTest(){
        saveNotes()
        val booksWithNote = dao!!.getBooksWithNotes(1)
        val book = dao!!.getBook(1)
        assert(booksWithNote.book?.name == book.name)
        assert(booksWithNote.notes?.size == 10)
    }

    @Test
    @Throws(Exception::class)
    fun getAllBooksWithNotesTest(){
        saveNotes()
        val booksWithNotes = dao!!.getAllBooksWithNotes()

        assert(booksWithNotes.size == 10)
        assert(booksWithNotes[0].notes?.size == 10)
    }
}