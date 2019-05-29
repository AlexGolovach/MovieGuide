package com.example.android.movie.ui.main.information.details.movie

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.Reviews
import com.example.android.movie.R
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.movie.ui.main.information.actor.ActorFragment
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.convertTime
import com.example.android.movie.ui.utils.getBundleWithId
import com.example.android.network.Converter
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.Cast
import com.example.android.network.models.moviesquad.MovieActorSquad
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_details.*

class MovieDetailsFragment : Fragment(), IMovieDetailsView {

    private lateinit var movieDetailsPresenter: IMovieDetailsPresenter

    private lateinit var movieActorsAdapter: MovieActorsAdapter
    private lateinit var recommendedMoviesAdapter: RecommendedMoviesAdapter
    private lateinit var movieVideosAdapter: MovieVideosAdapter
    private lateinit var movieReviewsAdapter: MovieReviewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movie_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieDetailsPresenter =
            MovieDetailsPresenter(this)

        getData()

        initToolbar()
        initRecyclerActors()
        initRecyclerRecommendedMovies()
        initRecyclerVideos()
        initRecyclerReviews()

        setShowHideListeners()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getData() {
        val movieId = arguments?.getInt("MOVIE_ID")

        movieId?.let { id ->
            movieDetailsPresenter.checkMovieInFavorites(movieId)
            movieDetailsPresenter.onDownloadMovieDetails(id)
            movieDetailsPresenter.onDownloadActorSquad(id)
            movieDetailsPresenter.onDownloadRecommendedMovies(id)
            movieDetailsPresenter.onDownloadVideo(id)
            movieDetailsPresenter.loadReviewForFilm(movieId)

            icon_send_review.setOnClickListener {
                writeReview(movieId)
            }

            icon_like.setOnClickListener {
                icon_like.setImageResource(R.drawable.ic_check)
                icon_like.isClickable = false
                movieDetailsPresenter.addMovieToFavorites(id)
            }
        }
    }

    private fun initRecyclerActors() {
        val context = recycler_view_actor.context

        movieActorsAdapter =
            MovieActorsAdapter()

        recycler_view_actor.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))

            val listener = object :
                MovieActorsAdapter.Listener {
                override fun onItemClicked(actor: Cast) {

                    val actorFragment = ActorFragment()
                    actorFragment.arguments =
                        getBundleWithId("ACTOR_ID", actor.id)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(actor.name)
                        ?.replace(R.id.container, actorFragment, actor.name)
                        ?.commit()
                }
            }

            movieActorsAdapter.listener = listener
            adapter = movieActorsAdapter
        }
    }

    private fun initRecyclerRecommendedMovies() {
        val context = recycler_view_recommended_movies.context

        recommendedMoviesAdapter =
            RecommendedMoviesAdapter()

        recycler_view_recommended_movies.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object :
                RecommendedMoviesAdapter.Listener {
                override fun onItemClicked(movie: Movie) {

                    val movieDetailsFragment =
                        MovieDetailsFragment()
                    movieDetailsFragment.arguments =
                        getBundleWithId("MOVIE_ID", movie.id)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(movie.title)
                        ?.replace(
                            R.id.container,
                            movieDetailsFragment,
                            movie.title
                        )
                        ?.commit()
                }
            }

            recommendedMoviesAdapter.listener = listener
            adapter = recommendedMoviesAdapter
        }
    }

    private fun initRecyclerVideos() {
        val context = recycler_view_videos.context

        movieVideosAdapter =
            MovieVideosAdapter()

        recycler_view_videos.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            adapter = movieVideosAdapter
        }
    }

    private fun initRecyclerReviews() {
        val context = recycler_view_reviews.context

        movieReviewsAdapter =
            MovieReviewsAdapter()

        recycler_view_reviews.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val listener = object :
                MovieReviewsAdapter.DeleteListener {
                override fun onDeleteClickedListener(reviews: Reviews) {
                    movieDetailsPresenter.deleteReviewForFilm(reviews.documentId)
                }
            }

            movieReviewsAdapter.deleteListener = listener
            adapter = movieReviewsAdapter
        }
    }

    private fun setShowHideListeners() {
        icon_show_video.setOnClickListener {
            icon_show_video.visibility = View.GONE
            icon_hide_video.visibility = View.VISIBLE
            recycler_view_videos.visibility = View.VISIBLE
        }

        icon_hide_video.setOnClickListener {
            icon_hide_video.visibility = View.GONE
            icon_show_video.visibility = View.VISIBLE
            recycler_view_videos.visibility = View.GONE
        }

        icon_show_comment.setOnClickListener {
            icon_show_comment.visibility = View.GONE
            icon_hide_comment.visibility = View.VISIBLE
            recycler_view_reviews.visibility = View.VISIBLE
        }

        icon_hide_comment.setOnClickListener {
            icon_hide_comment.visibility = View.GONE
            icon_show_comment.visibility = View.VISIBLE
            recycler_view_reviews.visibility = View.GONE
        }
    }

    private fun writeReview(movieId: Int) {
        val review = write_review_edit_text.text.toString()

        if (!TextUtils.isEmpty(review)) {

            movieDetailsPresenter.addReviewForFilm(
                movieId,
                AccountOperation.getAccount().login,
                review
            )

            write_review_edit_text.text = null

            movieDetailsPresenter.loadReviewForFilm(movieId)
        }
    }

    override fun onDownloadResultDetails(movie: MovieDetails) {
        val imageUrl = movie.image?.let { Converter.getImageUrl(it) }

        release_date_text.text = movie.releaseDate
        runtime_text.text = convertTime(movie.runtime)
        movie_description_text.text = movie.description

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
            .into(poster_image)

        collapsing_toolbar.title = movie.title

    }

    override fun onDownloadActorSquad(actorSquad: MovieActorSquad) {
        movieActorsAdapter.setItems(actorSquad)
    }

    override fun onDownloadRecommendedMovies(movies: MovieList) {
        recommendedMoviesAdapter.setItems(movies)
    }

    override fun onDownloadVideo(videos: List<String>) {
        movieVideosAdapter.setItems(videos)
    }

    override fun onDownloadReviewsForMovie(result: ArrayList<Reviews>) {
        movieReviewsAdapter.updateItems(result)
    }

    override fun isMovieAddedInFavorites(result: Boolean) {
        when (result) {
            true -> {
                icon_like.setImageResource(R.drawable.ic_check)
                icon_like.isClickable = false
            }
        }
    }

    override fun onDownloadDetailsError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
        collapsing_toolbar.visibility = View.GONE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        details_layout.visibility = View.VISIBLE
        collapsing_toolbar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        movieDetailsPresenter.onDestroy()
        super.onDestroy()
    }
}