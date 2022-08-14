package com.example.whattowatch.api

import com.example.whattowatch.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object MovieController {

    val api: MovieApi by lazy {
        Retrofit.Builder().baseUrl("http://10.0.2.2:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}