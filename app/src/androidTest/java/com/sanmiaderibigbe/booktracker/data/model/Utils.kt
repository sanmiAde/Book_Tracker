package com.sanmiaderibigbe.booktracker.data.model

import com.sanmiaderibigbe.booktracker.data.model.Book
import com.sanmiaderibigbe.booktracker.data.model.BookState
import java.util.*

fun createBook(name: Int): Book = Book(name = "ssa$name",author = "sds",genre= "dfd", numberOfPages = 12, rating= 4.0, readYear = "2018",state= BookState.READING,created_at =  Date(), updated_at =  Date())

fun createReadingBook(name: Int): Book = Book(name = "ssa$name",author = "sds",genre= "dfd", numberOfPages = 12, rating= 4.0, readYear = "2018",state= BookState.READING, created_at =  Date(), updated_at =  Date())

fun createBookRead(name: Int): Book = Book(name = "ssa$name",author = "sds",genre= "dfd", numberOfPages = 12, rating= 4.0, readYear = "2018",state= BookState.READ,created_at =  Date(), updated_at =  Date())

fun createBookToRead(name: Int): Book = Book(name = "ssa$name",author = "sds",genre= "dfd", numberOfPages = 12, rating= 4.0, readYear = "2018",state= BookState.TO_READ, created_at =  Date(), updated_at =  Date())

fun completeBook(name: Int, goalId: Long):  Book = Book(name = "ssa$name",author = "sds",genre= "dfd", numberOfPages = 12, rating= 4.0, readYear = "2018",state= BookState.TO_READ, created_at =  Date(), updated_at =  Date(), goalId = goalId)

fun createNote(name: Int, bookId: Int) : Note = Note("sdskndsk$name", Date(), Date(), bookId)

fun createGoal(year: String): Goal = Goal(0,0, year,Date(), Date())