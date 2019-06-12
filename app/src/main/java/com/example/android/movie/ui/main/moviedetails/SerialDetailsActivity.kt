package com.example.android.movie.ui.main.moviedetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R
import com.example.android.movie.ui.main.moviedetails.serial.SerialDetailsFragment
import com.example.android.movie.ui.utils.getBundleWithId

class SerialDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_serial_details)

        val showDetailsFragment =
            SerialDetailsFragment()
        showDetailsFragment.arguments = getBundleWithId(
            "SERIAL_ID",
            intent.getIntExtra("SERIAL_ID", 0)
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, showDetailsFragment)
            .commit()
    }
}