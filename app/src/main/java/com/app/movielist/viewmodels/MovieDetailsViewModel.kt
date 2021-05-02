package com.app.movielist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.movielist.models.MovieDetailsResponse
import com.app.movielist.repo.MainRepository
import com.app.movielist.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(private val movieRepository: MainRepository) : ViewModel() {

    private var movieDetails = MutableLiveData<Resource<MovieDetailsResponse>>()
    private val compositeDisposable = CompositeDisposable()

    fun movieDetails(movieId: Int) {
        movieDetails.postValue(Resource.loading(null))
        compositeDisposable.add(
            movieRepository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { movieDetails.postValue(Resource.success(it)) },
                    { t -> movieDetails.postValue(Resource.error(t.toString(), null)) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }

    fun getMovieDetails() = movieDetails

    fun getStars(vote_average: Double): Float {
        val outOf10 = (vote_average / 10) * 100
        val outOf5 = 0.05 * outOf10
        return outOf5.toFloat()
    }
}