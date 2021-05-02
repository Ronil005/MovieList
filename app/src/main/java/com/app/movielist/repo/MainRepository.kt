package com.app.movielist.repo

class MainRepository(private val apiHelper: APIHelper) {
    fun getMovieDetails(movieId: Int) = apiHelper.getMovieDetails(movieId)
    fun getPopularMovies() = apiHelper.getPopularMovies()
}