package com.example.android.movie.ui.main.details.movie

import ImageLoader
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video
import com.example.android.movie.R
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.movie.ui.base.BaseFragment
import com.example.android.movie.ui.main.details.PlayerFragment
import com.example.android.movie.ui.main.details.actor.ActorFragment
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.Constants.ACTOR_ID
import com.example.android.movie.ui.utils.Constants.FILM
import com.example.android.movie.ui.utils.Constants.MOVIE_ID
import com.example.android.movie.ui.utils.Constants.VIDEO_ID
import com.example.android.movie.ui.utils.getBundleWithId
import com.example.android.movie.ui.utils.getBundleWithVideo
import kotlinx.android.synthetic.main.fragment_movie_details.*
import java.lang.ref.WeakReference

class MovieDetailsFragment : BaseFragment(), IMovieDetailsView,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var movieDetailsPresenter: IMovieDetailsPresenter
    private lateinit var movieActorsAdapter: MovieActorsAdapter
    private lateinit var recommendedMoviesAdapter: RecommendedMoviesAdapter
    private lateinit var movieVideosAdapter: MovieVideosAdapter

    private var movieId: Int? = null
    private lateinit var handler: Handler

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
        movieVideosAdapter = MovieVideosAdapter()

        movieDetailsPresenter =
            MovieDetailsPresenter(isOnline(), this)

        handler = Handler()

        swipeRefreshLayout.setOnRefreshListener(this)

        checkNetworkConnection()

        initRecyclerActors()
        initRecyclerRecommendedMovies()
        initRecyclerVideos()
    }

    private fun checkNetworkConnection() {
        if (isOnline()) {
            getData()
        } else {
            checkConnection()
        }
    }

    override fun onRefresh() {
        handler.postDelayed({
            swipeRefreshLayout.isRefreshing = false
            checkNetworkConnection()
        }, 2000)
    }

    private fun getData() {
        movieId = arguments?.getInt(MOVIE_ID)

        movieId?.let {
            movieDetailsPresenter.apply {
                checkMovieInFavorites(AccountOperation.getAccount().id, it)
                onDownloadMovieDetails(it)
                onDownloadActorSquad(it)
                onDownloadRecommendedMovies(it)
                onDownloadVideo(it)
            }
        }
    }

    override fun isMovieInFavorites(result: Boolean) {
        initToolbar(result)
    }

    private fun initToolbar(isFavorite: Boolean) {
        movieId = arguments?.getInt(MOVIE_ID)

        activity?.let { ContextCompat.getColor(it, R.color.white) }?.let {
            collapsingToolbar.setExpandedTitleColor(
                it
            )
        }

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                onBack()
            }

            inflateMenu(R.menu.toolbar_menu)

            when (isFavorite) {
                true -> {
                    menu.findItem(R.id.addToFavorite).isVisible = false
                    menu.findItem(R.id.deleteFromFavorite).isVisible = true
                }
                false -> {
                    menu.findItem(R.id.addToFavorite).isVisible = true
                    menu.findItem(R.id.deleteFromFavorite).isVisible = false
                }
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.addToFavorite -> {
                        movieId?.let { id ->
                            movieDetailsPresenter.addToFavorite(id, FILM)
                            movieDetailsPresenter.checkMovieInFavorites(
                                AccountOperation.getAccount().id,
                                id
                            )
                        }

                        it.isVisible = false
                        menu.findItem(R.id.deleteFromFavorite).isVisible = true

                        true
                    }
                    R.id.deleteFromFavorite -> {
                        movieId?.let { id ->
                            movieDetailsPresenter.deleteFromFavorite(
                                AccountOperation.getAccount().id,
                                id
                            )
                            movieDetailsPresenter.checkMovieInFavorites(
                                AccountOperation.getAccount().id,
                                id
                            )
                        }

                        it.isVisible = false
                        menu.findItem(R.id.addToFavorite).isVisible = true

                        true
                    }
                    else -> {
                        super.onOptionsItemSelected(it)
                    }
                }
            }
        }
    }

    private fun initRecyclerActors() {
        val context = recyclerViewActor.context

        recyclerViewActor.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))

            val listener = object : MovieActorsAdapter.Listener {
                override fun onItemClicked(actor: ActorSquad) {

                    val actorFragment = ActorFragment()
                    actorFragment.arguments =
                        getBundleWithId(ACTOR_ID, actor.id)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(actor.actorName)
                        ?.replace(R.id.container, actorFragment, actor.actorName)
                        ?.commit()
                }
            }

            movieActorsAdapter.listener = listener
            adapter = movieActorsAdapter
        }
    }

    private fun initRecyclerRecommendedMovies() {
        val context = recyclerViewRecommendedMovies.context

        recyclerViewRecommendedMovies.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object : RecommendedMoviesAdapter.Listener {
                override fun onItemClicked(movie: Movie) {

                    val movieDetailsFragment = MovieDetailsFragment()
                    movieDetailsFragment.arguments =
                        getBundleWithId(MOVIE_ID, movie.movieOrSerialId)

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
        val context = recyclerViewVideos.context
        recyclerViewVideos.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))

            val listener = object : MovieVideosAdapter.OnClickListener {
                override fun onItemClicked(video: Video) {
                    val playerFragment = PlayerFragment()
                    playerFragment.arguments =
                        getBundleWithVideo(VIDEO_ID, video.videoId)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(video.videoId)
                        ?.replace(R.id.container, playerFragment,video.videoId)
                        ?.commit()
                }

            }

            movieVideosAdapter.listener = listener

            adapter = movieVideosAdapter
        }
    }

    override fun onDownloadResultDetails(movie: Details) {
        releaseDateText.text = movie.date
        runtimeText.text = movie.runtime
        movieDescriptionText.text = movie.description
        ImageLoader.getInstance()?.load(movie.image, WeakReference(posterImage))
        collapsingToolbar.title = movie.title
    }

    override fun onDownloadActorSquad(actorSquad: List<ActorSquad>) {
        movieActorsAdapter.setItems(actorSquad)
    }

    override fun onDownloadRecommendedMovies(movies: List<Movie>) {
        recommendedMoviesAdapter.setItems(movies)
    }

    override fun onDownloadVideo(videos: List<Video>) {
        if (videos.isEmpty()) {
            videoText.visibility = View.GONE
        } else {
            movieVideosAdapter.setItems(videos)
        }
    }

    override fun onDownloadDetailsError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        detailsLayout.visibility = View.GONE
        collapsingToolbar.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        detailsLayout.visibility = View.VISIBLE
        collapsingToolbar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        movieDetailsPresenter.onDestroy()
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }
}