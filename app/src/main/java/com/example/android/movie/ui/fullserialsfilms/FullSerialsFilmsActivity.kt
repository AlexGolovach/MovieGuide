package com.example.android.movie.ui.fullserialsfilms

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.example.android.movie.R
import com.example.android.movie.ui.base.search.IFragmentListener
import com.example.android.movie.ui.base.search.ISearch
import com.example.android.movie.ui.fullserialsfilms.fullfilms.FullFilmsFragment
import com.example.android.movie.ui.fullserialsfilms.fullserials.FullSerialsFragment
import com.example.android.movie.ui.utils.TabAdapter
import kotlinx.android.synthetic.main.activity_full_films_serials.*

class FullSerialsFilmsActivity: AppCompatActivity(), SearchView.OnQueryTextListener, IFragmentListener{

    private val fragmentPagerAdapter = TabAdapter(supportFragmentManager)
    private var iSearch: ArrayList<ISearch> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_full_films_serials)

        setSupportActionBar(toolbar)

        initToolbar()
        initViewPager()

        tablayout.setupWithViewPager(viewpager)
    }

    private fun initToolbar() {
        toolbar.apply {
            title = resources.getString(R.string.films_and_serials)
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
            }
        }
    }

    private fun initViewPager() {
        fragmentPagerAdapter.addFragment(FullFilmsFragment(), "Films")
        fragmentPagerAdapter.addFragment(FullSerialsFragment(), "Serials")

        viewpager.adapter = fragmentPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as android.support.v7.widget.SearchView

        searchView.queryHint = resources.getString(R.string.search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
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

    override fun onQueryTextSubmit(query: String): Boolean {
        setTextQuery(query)

        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        setTextQuery(query)

        return true
    }
}