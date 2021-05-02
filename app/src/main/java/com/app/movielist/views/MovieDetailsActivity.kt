package com.app.movielist.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.movielist.BR
import com.app.movielist.R
import com.app.movielist.databinding.ActivityMovieDetailsBinding
import com.app.movielist.repo.APIHelper
import com.app.movielist.repo.APIInterfaceImpl
import com.app.movielist.utils.Status
import com.app.movielist.viewmodels.MovieDetailsViewModel
import com.app.movielist.viewmodels.ViewModelFactory

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var binding: ActivityMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpVM()
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.getMovieDetails().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    binding.setVariable(BR.model, it.data!!)
                }
                Status.ERROR -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setUpVM() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(APIHelper(APIInterfaceImpl())))
            .get(MovieDetailsViewModel::class.java)

        binding.viewModel = viewModel
        viewModel.movieDetails(intent.getIntExtra("MOVIE_ID", 0))
    }

}