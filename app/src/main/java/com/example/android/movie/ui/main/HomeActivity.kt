package com.example.android.movie.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.android.movie.ui.base.BaseActivity
import com.example.android.movie.R
import com.example.android.movie.search.IFragmentListener
import com.example.android.movie.search.ISearch
import com.example.android.movie.ui.main.favorite.FavoriteActivity
import com.example.android.movie.ui.main.topmovies.TopMoviesFragment
import com.example.android.movie.ui.main.topserials.TopSerialsFragment
import com.example.android.movie.ui.profile.ProfileActivity
import com.example.android.movie.ui.register.RegisterActivity
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.TabAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.drawer_header.view.*

class HomeActivity : BaseActivity(), SearchView.OnQueryTextListener, IFragmentListener,
    SwipeRefreshLayout.OnRefreshListener {

    private var iSearch = mutableListOf<ISearch>()

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        handler = Handler()

        setSupportActionBar(toolbar)

        tabLayout.setupWithViewPager(viewPager)

        initDrawer()

        swipeRefreshLayout.setOnRefreshListener(this)

        if (isOnline()) {
            initViewPager()
        } else {
            checkConnection()
        }
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
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.favorites -> {
                    startActivity(Intent(this, FavoriteActivity::class.java))
                    drawerLayout.closeDrawer(GravityCompat.START)
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

    override fun onRefresh() {
        handler.postDelayed({
            swipeRefreshLayout.isRefreshing = false
            if (isOnline()) {
                initViewPager()
            } else {
                checkConnection()
            }
        }, 2000)
    }

    private fun initViewPager() {
        val fragmentPagerAdapter = TabAdapter(supportFragmentManager)

        fragmentPagerAdapter.addFragment(TopMoviesFragment(), "Top Movies")
        fragmentPagerAdapter.addFragment(TopSerialsFragment(), "Top Serials")

        viewPager.adapter = fragmentPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.actionSearch).actionView as SearchView

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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacksAndMessages(null)
    }
}