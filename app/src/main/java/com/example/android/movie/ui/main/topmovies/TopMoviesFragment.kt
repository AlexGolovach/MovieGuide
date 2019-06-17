package com.example.android.movie.ui.main.topmovies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.topmovies.ITopMoviesPresenter
import com.example.android.movie.mvp.topmovies.ITopMoviesView
import com.example.android.movie.search.IFragmentListener
import com.example.android.movie.search.ISearch
import com.example.android.movie.ui.base.BaseFragment
import com.example.android.movie.ui.main.details.MovieDetailsActivity
import com.example.android.movie.ui.utils.Constants.MOVIE_ID
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import kotlinx.android.synthetic.main.fragment_top_movies.*

class TopMoviesFragment : BaseFragment(), ITopMoviesView, ISearch {

    private lateinit var topMoviesPresenter: ITopMoviesPresenter

    private lateinit var topAdapter: TopMoviesAdapter
    private lateinit var searchResultAdapter: SearchResultMoviesAdapter

    private lateinit var iFragmentListener: IFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iFragmentListener = context as IFragmentListener
        iFragmentListener.addiSearch(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_top_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topAdapter = TopMoviesAdapter()
        topMoviesPresenter = TopMoviesPresenter(this)

        getData()

        initRecycler()
        initSearchResultRecycler()
    }

    private fun getData() {
        topMoviesPresenter.onDownloadMovies()
    }

    private fun initRecycler() {
        val context = recyclerView.context

        recyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(context, VERTICAL, false)

            layoutManager = linearLayoutManager
            setHasFixedSize(true)

            val listener = object : TopMoviesAdapter.Listener {
                override fun onItemClicked(movie: Movie) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)

                    intent.putExtra(MOVIE_ID, movie.id)

                    startActivity(intent)
                }
            }

            val loadItems = object : TopMoviesAdapter.LoadItems {
                override fun onLoadItems(page: Int) {
                    if (isOnline()) {
                        topMoviesPresenter.onDownloadMovies(page)
                    }
                }

            }

            topAdapter.listener = listener
            topAdapter.loadListener = loadItems

            adapter = topAdapter
        }
    }

    private fun initSearchResultRecycler() {
        val context = searchResultRecyclerView.context

        searchResultAdapter = SearchResultMoviesAdapter()

        searchResultRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            val listener = object : SearchResultMoviesAdapter.OpenListener {
                override fun onItemClickedListener(movie: Movie) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)

                    intent.putExtra(MOVIE_ID, movie.id)

                    startActivity(intent)
                }
            }

            searchResultAdapter.openListener = listener
            adapter = searchResultAdapter
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDownloadResult(movies: MovieList) {
        topAdapter.updateItems(movies)
    }

    override fun onTextQuery(text: String) {
        if (text.isEmpty()) {
            recyclerView.visibility = View.VISIBLE
            searchResultRecyclerView.visibility = View.GONE

            searchResultAdapter.updateItems(emptyList())
        } else {
            recyclerView.visibility = View.GONE
            searchResultRecyclerView.visibility = View.VISIBLE
            topMoviesPresenter.onSearchMovies(text)
        }
    }

    override fun onSearchResult(result: MovieList) {
        searchResultAdapter.updateItems(result.results)
    }

    override fun onDownloadError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        iFragmentListener.removeISearch(this)
    }

    override fun onDestroy() {
        topMoviesPresenter.onDestroy()
        super.onDestroy()
    }
}