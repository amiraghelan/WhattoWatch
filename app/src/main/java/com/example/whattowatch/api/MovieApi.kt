package com.example.whattowatch.api

import com.example.whattowatch.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MovieApi {
    @GET("/now_playing")
    fun getCurrentlyPlayingMovies(@Query("page")page:Int=1): Call<ApiAllMoviesResponse>

    @GET("/movies_likes")
    fun getMovieDetails(@Query("user_id") userId : Int, @Query("movie_id") movieId :Int) : Call<ApiMovieDetailsResponse>

    @POST("/point")
    fun Vote(@Body body : ApiVoteRequest) : Call<ApiVoteResponse>
}