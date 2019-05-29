package com.example.android.movie.ui.main.information

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R
import com.example.android.movie.ui.main.information.details.movie.MovieDetailsFragment
import com.example.android.movie.ui.utils.getBundleWithId

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)

        val movieDetailsFragment =
            MovieDetailsFragment()
        movieDetailsFragment.arguments = getBundleWithId(
            "MOVIE_ID",
            intent.getIntExtra("MOVIE_ID", 0)
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, movieDetailsFragment)
            .commit()
    }
}