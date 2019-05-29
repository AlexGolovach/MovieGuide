package com.example.android.movie.ui.main.information

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R
import com.example.android.movie.ui.main.information.details.show.ShowDetailsFragment
import com.example.android.movie.ui.utils.getBundleWithId

class ShowDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)

        val showDetailsFragment =
            ShowDetailsFragment()
        showDetailsFragment.arguments = getBundleWithId(
            "SHOW_ID",
            intent.getIntExtra("SHOW_ID", 0)
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, showDetailsFragment)
            .commit()
    }
}