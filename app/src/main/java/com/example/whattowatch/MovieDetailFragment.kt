package com.example.whattowatch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.whattowatch.api.MovieController
import com.example.whattowatch.model.ApiMovieDetailsResponse
import com.example.whattowatch.model.ApiVoteRequest
import com.example.whattowatch.model.ApiVoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MovieDetailFragment : Fragment() {

    private lateinit var imgPoster: ImageView
    private lateinit var btnUpVote: ImageButton
    private lateinit var btnDownVote: ImageButton
    private lateinit var tvTitle: TextView
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvLikes: TextView

    private var userVote: Int? = null

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        imgPoster = v.findViewById(R.id.img_poster)
        btnUpVote = v.findViewById(R.id.imb_upvote)
        btnDownVote = v.findViewById(R.id.imb_downvote)
        tvTitle = v.findViewById(R.id.tv_title)
        tvReleaseDate = v.findViewById(R.id.tv_releaseDate)
        tvLikes = v.findViewById(R.id.tv_likes)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTitle.text = args.movie.title
        tvReleaseDate.text = args.movie.releaseDate
        Glide.with(requireContext()).load(args.movie.posterSmallPath).into(imgPoster)

        btnUpVote.setOnClickListener {
            if (userVote != 1) {
                voteMovie(1)
                userVote = 1
                return@setOnClickListener
            }
            voteMovie(-1)
            userVote = 0
        }
        btnDownVote.setOnClickListener {
            if (userVote != -1) {
                voteMovie(-1)
                userVote = -1
                return@setOnClickListener
            }
            voteMovie(1)
            userVote = 0
        }

        fetchData()
    }


    private fun fetchData() {
        val response = MovieController.api.getMovieDetails(1, args.movie.id)
        response.enqueue(object : Callback<ApiMovieDetailsResponse?> {
            override fun onResponse(
                call: Call<ApiMovieDetailsResponse?>,
                response: Response<ApiMovieDetailsResponse?>
            ) {
                tvLikes.text = response.body()?.totalLikes!!.toString()
                userVote = response.body()?.userLikeValue
                Log.d("TAAAAAAG", "onResponse: $userVote")

            }

            override fun onFailure(call: Call<ApiMovieDetailsResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun voteMovie(point: Int) {
        val body = ApiVoteRequest(1, args.movie.id, point)
        val response = MovieController.api.Vote(body)
        response.enqueue(object : Callback<ApiVoteResponse?> {
            override fun onResponse(
                call: Call<ApiVoteResponse?>,
                response: Response<ApiVoteResponse?>
            ) {
                Toast.makeText(
                    requireContext(),
                    "${response.body()?.status}",
                    Toast.LENGTH_SHORT
                ).show()
                val totalPoints = tvLikes.text.toString().toInt()
                tvLikes.text = (totalPoints + point).toString()
            }

            override fun onFailure(call: Call<ApiVoteResponse?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }


}