package com.example.android.movie.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.User
import com.example.android.movie.R
import com.example.android.movie.mvp.home.IHomePresenter
import com.example.android.movie.mvp.home.IHomeView
import com.example.android.movie.ui.favorites.FavoritesActivity
import com.example.android.movie.ui.main.search.SearchFragment
import com.example.android.movie.ui.main.topmovies.TopMoviesFragment
import com.example.android.movie.ui.main.topshows.TopShowsFragment
import com.example.android.movie.ui.profile.ProfileActivity
import com.example.android.movie.ui.register.RegisterActivity
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.TabAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_header.view.*

class HomeActivity : AppCompatActivity(), IHomeView {

    private lateinit var homePresenter: IHomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        homePresenter = HomePresenter(this)

        setSupportActionBar(toolbar)

        initDrawer()
        initViewPager()

        tablayout.setupWithViewPager(viewpager)
    }

    private fun initViewPager() {
        val fragmentPagerAdapter = TabAdapter(supportFragmentManager)

        fragmentPagerAdapter.addFragment(TopMoviesFragment(), "Top Movies")
        fragmentPagerAdapter.addFragment(TopShowsFragment(), "Top Shows")

        viewpager.adapter = fragmentPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_search, menu)

        val menuItem = menu?.findItem(R.id.action_search)

        menuItem?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction()
                        .addToBackStack(SearchFragment::class.java.name)
                        .replace(R.id.container, SearchFragment(), SearchFragment::class.java.name)
                        .commit()

                    true
                }

                else -> {
                    false
                }
            }
        }

        return true
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
        header.header_username.text = AccountOperation.getAccount().login

        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    startActivity(Intent(this, FavoritesActivity::class.java))
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                R.id.exit -> {
                    homePresenter.closeAccount()
                }
            }
            true
        }
    }

    override fun closeAccount() {
        startActivity(Intent(this, RegisterActivity::class.java))

        finish()
    }

    override fun closeAccountError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        homePresenter.onDestroy()

        super.onDestroy()
    }
}