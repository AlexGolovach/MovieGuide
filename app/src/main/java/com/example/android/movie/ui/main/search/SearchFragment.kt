package com.example.android.movie.ui.main.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.search.ISearchMoviesPresenter
import com.example.android.movie.mvp.search.ISearchMoviesView
import com.example.android.network.models.movie.MovieList
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment(), ISearchMoviesView {

    private lateinit var searchMoviesPresenter: ISearchMoviesPresenter
    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchMoviesPresenter = SearchPresenter(this)

        initRecycler()
    }

    private fun initRecycler() {
        val context = search_result_recycler_view.context

        searchResultAdapter = SearchResultAdapter()

        search_result_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            adapter = searchResultAdapter
        }
    }

    override fun onSearchResult(movies: MovieList) {
        searchResultAdapter.updateItems(movies)
    }

    override fun onSearchError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, "No movies", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        searchMoviesPresenter.onDestroy()

        super.onDestroy()
    }
}