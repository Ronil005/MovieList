package com.app.movielist.repo

import com.app.movielist.models.MovieDetailsResponse
import com.app.movielist.models.PopularMoviesResponse
import io.reactivex.Observable

class APIInterfaceImpl : APIInterface {
    override fun getPopularMovies(): Observable<PopularMoviesResponse> {
        return RetrofitClient().retrofitClient().create(APIInterface::class.java)
            .getPopularMovies()
    }

    override fun getMovieDetails(movieId: Int): Observable<MovieDetailsResponse> {
        return RetrofitClient().retrofitClient().create(APIInterface::class.java)
            .getMovieDetails(movieId)
    }
}