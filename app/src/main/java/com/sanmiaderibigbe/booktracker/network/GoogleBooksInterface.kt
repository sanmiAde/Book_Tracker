package com.sanmiaderibigbe.booktracker.network

import com.sanmiaderibigbe.booktracker.network.model.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksInterface {

    @GET("/books/v1/volumes")
    fun getBooks(
            @Query("q") bookName: String
    ): Call<Item>
}