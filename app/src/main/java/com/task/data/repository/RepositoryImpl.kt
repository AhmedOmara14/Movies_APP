package com.task.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.common.Resource
import com.task.domain.repository.Repository
import com.task.data.remote.Api
import com.task.domain.model.all_movies.AllMoviesResponse
import com.task.domain.model.movie_details.MovieDetailsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) :
    Repository {

    override fun getMovies(
        apiKey: String,
        page: Int
    ): LiveData<Resource<AllMoviesResponse>> {
        val movieLiveData = MutableLiveData<Resource<AllMoviesResponse>>()
        movieLiveData.value = Resource.loading()
        api.getMovies(apiKey, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Response<AllMoviesResponse>>() {
                override fun onSuccess(response: Response<AllMoviesResponse>) {
                    movieLiveData.value = Resource.success(response.body())
                }

                override fun onError(e: Throwable) {
                    movieLiveData.value = Resource.error(e.message.toString())
                }
            }
            )
        return movieLiveData
    }

    override fun getMovieDetails(
        movie_id: String,
        apikey: String
    ): LiveData<Resource<MovieDetailsResponse>> {
        val movieDetailsLiveData = MutableLiveData<Resource<MovieDetailsResponse>>()
        movieDetailsLiveData.value = Resource.loading()
        api.getMovieDetails(movie_id, apikey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Response<MovieDetailsResponse>>() {
                override fun onSuccess(response: Response<MovieDetailsResponse>) {
                    movieDetailsLiveData.value = Resource.success(response.body())
                }

                override fun onError(e: Throwable) {
                    movieDetailsLiveData.value = Resource.error(e.message.toString())
                }
            }
            )
        return movieDetailsLiveData
    }


}