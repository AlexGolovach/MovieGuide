package com.example.android.movie.ui.main.topmovies

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.topmovies.ITopMoviesPresenter
import com.example.android.movie.mvp.topmovies.ITopMoviesView
import com.example.android.movie.ui.main.moviedetails.DetailsActivity
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import kotlinx.android.synthetic.main.fragment_top_movies.*
import java.lang.NullPointerException

class TopMoviesFragment : Fragment(), ITopMoviesView {

    private lateinit var topMoviesPresenter: ITopMoviesPresenter
    private lateinit var topAdapter: TopMoviesAdapter

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
    }

    private fun getData() {
        topMoviesPresenter.onDownloadMovies()
    }

    private fun initRecycler() {
        val context = recyclerView.context

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val listener = object : TopMoviesAdapter.Listener {
                override fun onItemClicked(movie: Movie) {
                    val intent = Intent(activity, DetailsActivity::class.java)

                    intent.putExtra("MOVIE_ID", movie.id)

                    startActivity(intent)
                }
            }

            topAdapter.listener = listener
            adapter = topAdapter
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDownloadResult(movies: MovieList) {
        topAdapter.setItems(movies)
    }

    override fun onDownloadError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, "No movies", Toast.LENGTH_SHORT).show()
        }
    }

    override fun hideLoading() {
        if (progressBar.visibility == View.VISIBLE) {
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        topMoviesPresenter.onDestroy()

        super.onDestroy()
    }
}