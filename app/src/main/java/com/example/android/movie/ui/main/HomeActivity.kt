package com.example.android.movie.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.android.movie.R
import com.example.android.movie.ui.main.search.SearchFragment
import com.example.android.movie.ui.main.topmovies.TopMoviesFragment
import com.example.android.movie.ui.profile.ProfileActivity
import com.example.android.movie.ui.register.RegisterActivity
import com.example.android.movie.ui.utils.AccountOperation
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_header.view.*

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction()
            .addToBackStack(TopMoviesFragment::javaClass.name)
            .replace(R.id.container, TopMoviesFragment(), TopMoviesFragment::class.java.name)
            .commit()

        setSupportActionBar(toolbar)

        initDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_search, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.search)

        menuItem.setOnMenuItemClickListener {
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
                R.id.profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
                R.id.exit -> {
                    startActivity(Intent(this, RegisterActivity::class.java))

                    AccountOperation.deleteAccountInformation()

                    finish()
                }
            }
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

    override fun onQueryTextSubmit(query: String?): Boolean {

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {

        return true
    }
}