package com.task.domain.model.all_movies

import com.google.gson.annotations.SerializedName

data class AllMoviesResponse(
    val page: Int,
    @SerializedName("results")
    val movies: ArrayList<Movie>,
)