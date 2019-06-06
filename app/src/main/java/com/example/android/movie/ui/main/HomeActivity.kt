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
            .replace(R.id.container, TopMoviesFragment(), TopMoviesFragment::class.java.name)
            .commit()

        setSupportActionBar(toolbar)

        initDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_search, menu)

        val menuItem = menu?.findItem(R.id.actionSearch)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.search)

        menuItem.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionSearch -> {
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
            drawerLayout,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val header = navigationView.getHeaderView(0)
        header.headerUsername.text = AccountOperation.getAccount().login

        navigationView.setNavigationItemSelectedListener {
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)

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