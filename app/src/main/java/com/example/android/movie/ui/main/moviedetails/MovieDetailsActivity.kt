package com.example.android.movie.ui.main.moviedetails

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.View
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.movie.ui.main.actor.ActorActivity
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieActorSquad
import com.example.android.network.models.movie.MovieDetails
import kotlinx.android.synthetic.main.activity_movie_details.*
import java.lang.NullPointerException

class MovieDetailsActivity : AppCompatActivity(),
    IMovieDetailsView {

    private lateinit var movieDetailsPresenter: IMovieDetailsPresenter
    private lateinit var movieActorsAdapter: MovieActorsAdapter
    private lateinit var recommendedMoviesAdapter: RecommendedMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)

        movieActorsAdapter = MovieActorsAdapter()
        recommendedMoviesAdapter = RecommendedMoviesAdapter()

        movieDetailsPresenter = MovieDetailsPresenter(this)

        getData()
        initRecyclerActors()
        initRecyclerRecommendedMovies()
    }

    private fun getData() {
        val movieId = intent.getLongExtra("MOVIE_ID", 0)

        movieDetailsPresenter.onDownloadMovieDetails(movieId)
        movieDetailsPresenter.onDownloadActorSquad(movieId)
        movieDetailsPresenter.onDownloadRecommendedMovies(movieId)
    }

    private fun initRecyclerActors() {
        val context = recycler_view_actor.context

        recycler_view_actor.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))

            val listener = object : MovieActorsAdapter.Listener {
                override fun onItemClicked(actor: MovieActorSquad) {
                    val intent = Intent(this@MovieDetailsActivity, ActorActivity::class.java)

                    intent.putExtra("ACTOR_ID", actor.id)

                    startActivity(intent)
                }
            }

            movieActorsAdapter.listener = listener
            adapter = movieActorsAdapter
        }
    }

    private fun initRecyclerRecommendedMovies() {
        val context = recycler_view_recommended_movies.context

        recycler_view_recommended_movies.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, HORIZONTAL))

            val listener = object : RecommendedMoviesAdapter.Listener {
                override fun onItemClicked(movie: Movie) {
                    val intent = Intent(this@MovieDetailsActivity, MovieDetailsActivity::class.java)

                    intent.putExtra("MOVIE_ID", movie.id)

                    startActivity(intent)
                }
            }

            recommendedMoviesAdapter.listener = listener
            adapter = recommendedMoviesAdapter
        }
    }

    override fun onDownloadResultDetails(movie: MovieDetails, poster: Bitmap) {
        movie_title_text.text = movie.movie.title
        movie_description_text.text = movie.movie.description
        movie_poster_image.setImageBitmap(poster)
    }

    override fun onDownloadActorSquad(actorSquad: List<MovieActorSquad>) {
        movieActorsAdapter.setItems(actorSquad)
    }

    override fun onDownloadRecommendedMovies(movies: List<Movie>) {
        recommendedMoviesAdapter.setItems(movies)
    }

    override fun onDownloadDetailsError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        movie_poster_image.visibility = View.VISIBLE
        movie_title_text.visibility = View.VISIBLE
        movie_description_text.visibility = View.VISIBLE
        recycler_view_actor.visibility = View.VISIBLE
        recommended_movies_text.visibility = View.VISIBLE
        recycler_view_recommended_movies.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        movieDetailsPresenter.onDestroy()
        super.onDestroy()
    }
}