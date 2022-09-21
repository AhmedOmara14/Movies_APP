package com.task.data.remote

import com.task.domain.model.all_movies.AllMoviesResponse
import com.task.domain.model.movie_details.MovieDetailsResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apikey: String,
        @Query("page") page: Int,
    ): Single<Response<AllMoviesResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movie_id: String,
        @Query("api_key") apikey: String,
    ): Single<Response<MovieDetailsResponse>>


}