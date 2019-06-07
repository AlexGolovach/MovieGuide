package com.example.android.movie.ui.main.moviedetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R
import com.example.android.movie.ui.main.moviedetails.details.MovieDetailsFragment
import com.example.android.movie.ui.utils.getBundleWithId
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        val movieDetailsFragment = MovieDetailsFragment()
        movieDetailsFragment.arguments = getBundleWithId(
            "MOVIE_ID",
            intent.getIntExtra("MOVIE_ID", 0)
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, movieDetailsFragment)
            .commit()
    }
}