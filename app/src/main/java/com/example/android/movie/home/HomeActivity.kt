package com.example.android.movie.home

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        setSupportActionBar(toolbar)

        initDrawer()
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val header = navigation_view.getHeaderView(0)

        navigation_view.setNavigationItemSelectedListener {

            true
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }
}