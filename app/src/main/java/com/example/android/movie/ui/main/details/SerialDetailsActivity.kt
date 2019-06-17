package com.example.android.movie.ui.main.details

import android.os.Bundle
import com.example.android.movie.ui.base.BaseActivity
import com.example.android.movie.R
import com.example.android.movie.ui.main.details.serial.SerialDetailsFragment
import com.example.android.movie.ui.utils.Constants.SERIAL_ID
import com.example.android.movie.ui.utils.getBundleWithId

class SerialDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        setFragment()
    }

    private fun setFragment() {
        val serialDetailsFragment =
            SerialDetailsFragment()
        serialDetailsFragment.arguments = getBundleWithId(
            SERIAL_ID,
            intent.getIntExtra(SERIAL_ID, 0)
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, serialDetailsFragment)
            .commit()
    }
}