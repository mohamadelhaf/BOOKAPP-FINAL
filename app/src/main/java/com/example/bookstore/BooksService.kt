package com.example.bookstore

import retrofit2.Call
import retrofit2.http.GET


interface BooksService {



    @GET("posts")
    fun getBooks(): Call<List<Books>>
}