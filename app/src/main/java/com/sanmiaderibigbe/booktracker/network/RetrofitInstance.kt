package com.sanmiaderibigbe.booktracker.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL: String = "http://ws.audioscrobbler.com/"

    internal fun initRetrofitInstance(): GoogleBooksInterface {

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

        return retrofit.create(GoogleBooksInterface::class.java)

    }
}