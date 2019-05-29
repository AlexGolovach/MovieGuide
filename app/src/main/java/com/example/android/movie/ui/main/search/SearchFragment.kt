package com.example.android.movie.ui.main.search

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.search.ISearchMoviesPresenter
import com.example.android.movie.mvp.search.ISearchMoviesView
import com.example.android.movie.ui.main.HomeActivity
import com.example.android.movie.ui.main.information.MovieDetailsActivity
import com.example.android.network.models.movie.Movie
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    private fun initRecycler() {
        val context = search_result_recycler_view.context

        searchResultAdapter = SearchResultAdapter()

        search_result_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            val listener = object : SearchResultAdapter.OpenListener {
                override fun onItemClickedListener(movie: Movie) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)

                    intent.putExtra("MOVIE_ID", movie.id)

                    startActivity(intent)
                }
            }

            searchResultAdapter.openListener = listener
            adapter = searchResultAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menu?.clear()
        menuInflater?.inflate(R.menu.toolbar_search, menu)

        val searchView =
            SearchView((context as HomeActivity).supportActionBar?.themedContext ?: context)
        menu?.findItem(R.id.action_search)?.apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMoviesPresenter.onSearchMovies(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchMoviesPresenter.onSearchMovies(newText)

                return true
            }
        })
        searchView.queryHint = getString(R.string.search)
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