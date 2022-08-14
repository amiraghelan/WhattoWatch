package com.example.whattowatch.model

data class ApiVoteRequest(
    val user_id : Int,
    val movie_id : Int,
    val point : Int
)