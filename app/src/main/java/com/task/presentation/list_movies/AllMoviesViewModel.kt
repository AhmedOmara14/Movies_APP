package com.task.presentation.list_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.task.common.Resource
import com.task.domain.model.all_movies.AllMoviesResponse
import com.task.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    fun getMovies(apiKey: String, page: Int): LiveData<Resource<AllMoviesResponse>> {
        return repository.getMovies(apiKey, page)
    }

}