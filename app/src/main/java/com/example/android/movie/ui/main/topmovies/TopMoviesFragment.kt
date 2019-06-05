package com.example.android.movie.ui.main.topmovies

import android.content.Context
import android.content.Intent
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
import com.example.android.movie.ui.base.search.IFragmentListener
import com.example.android.movie.ui.base.search.ISearch
import com.example.android.movie.mvp.topmovies.ITopMoviesPresenter
import com.example.android.movie.mvp.topmovies.ITopMoviesView
import com.example.android.movie.ui.main.information.MovieDetailsActivity
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import kotlinx.android.synthetic.main.fragment_top_movies.*
import java.lang.NullPointerException

class TopMoviesFragment : Fragment(), ITopMoviesView,
    ISearch {

    private lateinit var topMoviesPresenter: ITopMoviesPresenter
    private lateinit var topAdapter: TopMoviesAdapter
    private lateinit var searchResultAdapter: SearchResultMoviesAdapter

    private var mIFragmentListener: IFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mIFragmentListener = context as IFragmentListener
        mIFragmentListener!!.addiSearch(this)
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
        initResultRecycler()
    }

    private fun getData() {
        topMoviesPresenter.onDownloadMovies()
    }

    private fun initRecycler() {
        val context = recycler_view.context

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val listener = object : TopMoviesAdapter.Listener {
                override fun onItemClicked(movie: Movie) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)

                    intent.putExtra("MOVIE_ID", movie.id)

                    startActivity(intent)
                }
            }

            topAdapter.listener = listener
            adapter = topAdapter
        }
    }

    private fun initResultRecycler() {
        val context = search_result_recycler_view.context

        searchResultAdapter = SearchResultMoviesAdapter()

        search_result_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            val listener = object : SearchResultMoviesAdapter.OpenListener {
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

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onDownloadResult(movies: MovieList) {
        topAdapter.setItems(movies)
    }

    override fun onTextQuery(text: String) {
        if (text.isEmpty()) {
            recycler_view.visibility = View.VISIBLE
            search_result_recycler_view.visibility = View.GONE
        } else {
            recycler_view.visibility = View.GONE
            search_result_recycler_view.visibility = View.VISIBLE
            topMoviesPresenter.onSearchMovies(text)
        }
    }

    override fun onSearchResult(result: MovieList) {
        searchResultAdapter.updateItems(result)
    }

    override fun onDownloadError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, "No movies", Toast.LENGTH_SHORT).show()
        }
    }

    override fun hideLoading() {
        if (progress_bar.visibility == View.VISIBLE) {
            progress_bar.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
        }
    }

    override fun onDetach() {
        super.onDetach()
        mIFragmentListener?.removeISearch(this)
    }

    override fun onDestroy() {
        topMoviesPresenter.onDestroy()

        super.onDestroy()
    }
}