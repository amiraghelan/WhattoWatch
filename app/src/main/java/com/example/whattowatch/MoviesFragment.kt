package com.example.whattowatch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whattowatch.adapter.MovieAdapter
import com.example.whattowatch.api.MovieController
import com.example.whattowatch.model.ApiAllMoviesResponse
import com.example.whattowatch.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var pbLoading : ProgressBar

    private var lastPageFetched = 1
    private var initialFetch = false
    private val movies = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_movies, container, false)
        recyclerView = v.findViewById(R.id.rcl_movies)
        pbLoading = v.findViewById(R.id.pb_loading)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         movieAdapter = MovieAdapter(requireContext() ,movies) { movie ->
             Log.d("TAAAAAAAG", "onViewCreated: ${movie.posterSmallPath}")
             val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movie)
             findNavController().navigate(action)
         }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = movieAdapter




    }

    override fun onStart() {
        super.onStart()
        if(!initialFetch) {
            fetchMovies()
        }
    }

    private fun fetchMovies(page : Int = 1){
        pbLoading.isVisible = true
        initialFetch = true

        val response = MovieController.api.getCurrentlyPlayingMovies()

        response.enqueue(object : Callback<ApiAllMoviesResponse?> {
            override fun onResponse(call: Call<ApiAllMoviesResponse?>, response: Response<ApiAllMoviesResponse?>) {
                movies.addAll(response.body()?.movies!!)
                lastPageFetched = response.body()?.page!!
                movieAdapter.notifyDataSetChanged()
                pbLoading.isVisible = false

            }

            override fun onFailure(call: Call<ApiAllMoviesResponse?>, t: Throwable) {
                Toast.makeText(activity, "Failed to Load Movies!",Toast.LENGTH_SHORT).show()
                pbLoading.isVisible = false
            }
        })

    }








}