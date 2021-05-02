package com.app.movielist.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.app.movielist.R
import com.app.movielist.databinding.ActivityMainBinding
import com.app.movielist.models.Results
import com.app.movielist.repo.APIHelper
import com.app.movielist.repo.APIInterfaceImpl
import com.app.movielist.utils.Status
import com.app.movielist.viewmodels.MoviesViewModel
import com.app.movielist.viewmodels.ViewModelFactory
import com.app.movielist.views.adapter.MovieAdapter

class MainActivity : AppCompatActivity(), MovieAdapter.OnClickListener {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpVM()
        setUpObserver()
    }

    override fun onMovieClick(it: View, popularMoviesResponse: Results) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("MOVIE_ID", popularMoviesResponse.id)
        startActivity(intent)
    }

    private fun setUpVM() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(APIHelper(APIInterfaceImpl())))
            .get(MoviesViewModel::class.java)
        binding.viewModel = viewModel
    }

    private fun setUpObserver() {
        viewModel.getPopularMovies().observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    it.data?.let { response ->
                     binding.moviesRecyclerView.adapter = MovieAdapter(response,this)
                    }
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
}