package com.sanmiaderibigbe.booktracker.model

interface IBook {
    val name: String
    val author: String
    val genre: String
    val numberOfPages: Int
    val rating: Double?


}