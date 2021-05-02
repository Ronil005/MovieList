package com.app.movielist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.movielist.models.PopularMoviesResponse
import com.app.movielist.repo.MainRepository
import com.app.movielist.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviesViewModel(private val movieRepository: MainRepository) : ViewModel() {

    private var popularMovies = MutableLiveData<Resource<PopularMoviesResponse>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        getPopularMoviesList()
    }

    private fun getPopularMoviesList() {
        popularMovies.postValue(Resource.loading(null))
        compositeDisposable.add(movieRepository.getPopularMovies()
            .map { getTop10PopularMovie(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ popularMovies.postValue(Resource.success(it)) },
                { t -> popularMovies.postValue(Resource.error(t.toString(), null)) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }

    /*Filtering Top 10 movies by popularity parameter*/
    private fun getTop10PopularMovie(popularMoviesResponse: PopularMoviesResponse): PopularMoviesResponse {
        val result = popularMoviesResponse.results.sortedByDescending { it.popularity }.slice(0..9)
        return PopularMoviesResponse(result)
    }

    fun getPopularMovies() = popularMovies


}