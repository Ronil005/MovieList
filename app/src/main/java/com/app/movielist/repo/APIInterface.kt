package com.app.movielist.repo

import com.app.movielist.models.MovieDetailsResponse
import com.app.movielist.models.PopularMoviesResponse
import com.app.movielist.utils.APIConstant
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {
    @GET(APIConstant.popularMovies)
    fun getPopularMovies(): Observable<PopularMoviesResponse>

    @GET(APIConstant.prefix + "/{movieId}")
    fun getMovieDetails(
        @Path(value = "movieId") movieId: Int
    ): Observable<MovieDetailsResponse>

}