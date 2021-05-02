package com.app.movielist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.movielist.repo.APIHelper
import com.app.movielist.repo.MainRepository

class ViewModelFactory(private val apiHelper: APIHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(MainRepository(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}