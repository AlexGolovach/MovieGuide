package com.example.android.movie.ui.main.details

import android.os.Bundle
import com.example.android.movie.R
import com.example.android.movie.ui.base.BaseActivity
import com.example.android.movie.ui.main.details.movie.MovieDetailsFragment
import com.example.android.movie.ui.utils.Constants.MOVIE_ID
import com.example.android.movie.ui.utils.getBundleWithId

class MovieDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        setFilmFragment()
    }

    private fun setFilmFragment() {
        val movieDetailsFragment = MovieDetailsFragment()
        movieDetailsFragment.arguments = getBundleWithId(
            MOVIE_ID,
            intent.getIntExtra(MOVIE_ID, 0)
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, movieDetailsFragment)
            .commit()
    }
}