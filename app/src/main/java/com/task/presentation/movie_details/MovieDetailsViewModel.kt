package com.task.presentation.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.common.Resource
import com.task.domain.model.movie_details.MovieDetailsResponse
import com.task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    fun getMovieDetails(movieId:String,apiKey: String): LiveData<Resource<MovieDetailsResponse>> {
        return repository.getMovieDetails(movieId,apiKey)
    }

}