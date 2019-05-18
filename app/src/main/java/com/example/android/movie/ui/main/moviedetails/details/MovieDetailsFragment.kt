package com.example.android.movie.ui.main.moviedetails.details

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.movie.ui.getBundleWithId
import com.example.android.movie.ui.main.moviedetails.actor.ActorFragment
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.Cast
import com.example.android.network.models.moviesquad.MovieActorSquad
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment(), IMovieDetailsView {

    private lateinit var movieDetailsPresenter: IMovieDetailsPresenter
    private lateinit var movieActorsAdapter: MovieActorsAdapter
    private lateinit var recommendedMoviesAdapter: RecommendedMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieActorsAdapter =
            MovieActorsAdapter()
        recommendedMoviesAdapter =
            RecommendedMoviesAdapter()

        movieDetailsPresenter =
            MovieDetailsPresenter(this)

        getData()
        initRecyclerActors()
        initRecyclerRecommendedMovies()
    }

    private fun getData() {
        val movieId = arguments?.getInt("MOVIE_ID")

        movieId?.let {
            movieDetailsPresenter.onDownloadMovieDetails(it)
            movieDetailsPresenter.onDownloadActorSquad(it)
            movieDetailsPresenter.onDownloadRecommendedMovies(it)
        }
    }

    private fun initRecyclerActors() {
        val context = recycler_view_actor.context

        recycler_view_actor.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))

            val listener = object : MovieActorsAdapter.Listener {
                override fun onItemClicked(actor: Cast) {

                    val actorFragment = ActorFragment()
                    actorFragment.arguments = getBundleWithId("ACTOR_ID", actor.id)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(ActorFragment::class.java.name)
                        ?.replace(R.id.container, actorFragment, ActorFragment::class.java.name)
                        ?.commit()
                }
            }

            movieActorsAdapter.listener = listener
            adapter = movieActorsAdapter
        }
    }

    private fun initRecyclerRecommendedMovies() {
        val context = recycler_view_recommended_movies.context

        recycler_view_recommended_movies.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))

            val listener = object : RecommendedMoviesAdapter.Listener {
                override fun onItemClicked(movie: Movie) {

                    val movieDetailsFragment = MovieDetailsFragment()
                    movieDetailsFragment.arguments = getBundleWithId("MOVIE_ID", movie.id)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(MovieDetailsFragment::class.java.name)
                        ?.replace(
                            R.id.container,
                            movieDetailsFragment,
                            MovieDetailsFragment::class.java.name
                        )
                        ?.commit()
                }
            }

            recommendedMoviesAdapter.listener = listener
            adapter = recommendedMoviesAdapter
        }
    }

    override fun onDownloadResultDetails(movie: MovieDetails, poster: Bitmap) {
        movie_title_text.text = movie.title
        movie_description_text.text = movie.description
        movie_poster_image.setImageBitmap(poster)
    }

    override fun onDownloadActorSquad(actorSquad: MovieActorSquad) {
        movieActorsAdapter.setItems(actorSquad)
    }

    override fun onDownloadRecommendedMovies(movies: MovieList) {
        recommendedMoviesAdapter.setItems(movies)
    }

    override fun onDownloadDetailsError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
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