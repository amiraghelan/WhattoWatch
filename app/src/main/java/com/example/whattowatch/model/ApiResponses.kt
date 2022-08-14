package com.example.whattowatch.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


data class ApiAllMoviesResponse(
    val movies: List<Movie>,
    val totalPages: Int,
    val page: Int
)

data class ApiMovieDetailsResponse(
    val totalLikes : Int,
    val userLikeValue : Int
)

data class ApiVoteResponse(
    val status : String
)

@Parcelize
class Movie(val id: Int, val title: String,private val posterPath: String, val releaseDate: String) : Parcelable{

    val posterSmallPath: String
    get() = "https://image.tmdb.org/t/p/w342${posterPath}"
}





// https://image.tmdb.org/t/p/w342