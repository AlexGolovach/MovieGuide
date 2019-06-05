package com.example.android.movie.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.ui.base.search.IFragmentListener
import com.example.android.movie.ui.base.search.ISearch
import com.example.android.movie.mvp.home.IHomePresenter
import com.example.android.movie.mvp.home.IHomeView
import com.example.android.movie.ui.favorites.FavoritesActivity
import com.example.android.movie.ui.fullserialsfilms.FullSerialsFilmsActivity
import com.example.android.movie.ui.main.topmovies.TopMoviesFragment
import com.example.android.movie.ui.main.topshows.TopShowsFragment
import com.example.android.movie.ui.profile.ProfileActivity
import com.example.android.movie.ui.register.RegisterActivity
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.TabAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_header.view.*


class HomeActivity : AppCompatActivity(), IHomeView, SearchView.OnQueryTextListener,
    IFragmentListener {

    private lateinit var homePresenter: IHomePresenter
    private val fragmentPagerAdapter = TabAdapter(supportFragmentManager)

    private var iSearch: ArrayList<ISearch> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        homePresenter = HomePresenter(this)

        setSupportActionBar(toolbar)

        initDrawer()
        initViewPager()

        tablayout.setupWithViewPager(viewpager)
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
                R.id.watch_films_serials -> {
                    startActivity(Intent(this, FullSerialsFilmsActivity::class.java))
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

    private fun initViewPager() {
        fragmentPagerAdapter.addFragment(TopMoviesFragment(), "Top Movies")
        fragmentPagerAdapter.addFragment(TopShowsFragment(), "Top Shows")

        viewpager.adapter = fragmentPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.queryHint = resources.getString(R.string.search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        setTextQuery(query)

        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        setTextQuery(query)

        return true
    }

    private fun setTextQuery(text: String) {
        for (iSearchLocal in this.iSearch) {
            iSearchLocal.onTextQuery(text)
        }
    }

    override fun addiSearch(iSearch: ISearch) {
        this.iSearch.add(iSearch)
    }

    override fun removeISearch(iSearch: ISearch) {
        this.iSearch.remove(iSearch)
    }

    override fun closeAccount() {
        startActivity(Intent(this, RegisterActivity::class.java))

        finish()
    }

    override fun closeAccountError() {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
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