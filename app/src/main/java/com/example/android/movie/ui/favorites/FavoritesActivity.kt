package com.example.android.movie.ui.favorites

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.movie.R
import com.example.android.movie.ui.favorites.movies.FavoriteMoviesFragment
import com.example.android.movie.ui.favorites.shows.FavoriteShowFragment
import com.example.android.movie.ui.utils.TabAdapter
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorites)

        setSupportActionBar(toolbar)

        initViewPager()

        tablayout.setupWithViewPager(viewpager)
    }

    private fun initViewPager() {
        val fragmentPagerAdapter = TabAdapter(supportFragmentManager)

        fragmentPagerAdapter.addFragment(FavoriteMoviesFragment(), "Movies")
        fragmentPagerAdapter.addFragment(FavoriteShowFragment(), "Shows")

        viewpager.adapter = fragmentPagerAdapter
    }
}