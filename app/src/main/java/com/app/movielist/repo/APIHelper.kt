package com.app.movielist.repo

class APIHelper(private val apiInterface: APIInterface) {

    fun getPopularMovies() = apiInterface.getPopularMovies()

    fun getMovieDetails(movieId: Int) = apiInterface.getMovieDetails(movieId)
}