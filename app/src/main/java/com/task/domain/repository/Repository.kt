package com.task.domain.repository

import androidx.lifecycle.LiveData
import com.task.common.Resource
import com.task.domain.model.all_movies.AllMoviesResponse
import com.task.domain.model.movie_details.MovieDetailsResponse

interface Repository {
    fun getMovies(apiKey: String, page: Int): LiveData<Resource<AllMoviesResponse>>
    fun getMovieDetails(
        movie_id: String, apikey: String,
    ): LiveData<Resource<MovieDetailsResponse>>
}