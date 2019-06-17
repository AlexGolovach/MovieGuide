package com.example.android.movie.ui.main.details.actor

import ImageLoader
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.HORIZONTAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.Actor
import com.example.android.database.model.Movie
import com.example.android.movie.R
import com.example.android.movie.mvp.actor.IActorPresenter
import com.example.android.movie.mvp.actor.IActorView
import com.example.android.movie.ui.base.BaseFragment
import com.example.android.movie.ui.main.details.MovieDetailsActivity
import com.example.android.movie.ui.utils.Constants.ACTOR_ID
import com.example.android.movie.ui.utils.Constants.DIALOG_IMAGE
import com.example.android.movie.ui.utils.Constants.IMAGE_URL
import com.example.android.movie.ui.utils.Constants.MOVIE_ID
import com.example.android.movie.ui.utils.DialogImageFragment
import kotlinx.android.synthetic.main.fragment_actor.*
import java.lang.ref.WeakReference

class ActorFragment : BaseFragment(), IActorView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var actorPresenter: IActorPresenter
    private lateinit var actorImageAdapter: ActorImageAdapter
    private lateinit var actorMovieAdapter: ActorMovieAdapter

    private var dialogImage = DialogImageFragment()

    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_actor, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actorImageAdapter = ActorImageAdapter()
        actorMovieAdapter = ActorMovieAdapter()

        actorPresenter = ActorPresenter(isOnline(), this)

        handler = Handler()

        swipeRefreshLayout.setOnRefreshListener(this)

        checkNetworkConnection()

        initToolbar()
        initRecyclerImages()
        initRecyclerMovies()
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
        val actorId = arguments?.getInt(ACTOR_ID)

        actorId?.let {
            actorPresenter.onDownloadActorDetails(it)
            actorPresenter.onDownloadImageURLs(it)
            actorPresenter.onDownloadActorMovies(it)
        }
    }

    private fun initToolbar() {
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
        }
    }

    private fun initRecyclerImages() {
        val context = recyclerViewActorImages.context

        recyclerViewActorImages.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object : ActorImageAdapter.Listener {
                override fun onItemClicked(actorImage: String) {
                    val bundle = Bundle()
                    bundle.putString(IMAGE_URL, actorImage)

                    dialogImage.arguments = bundle
                    dialogImage.show(fragmentManager, DIALOG_IMAGE)
                }
            }

            actorImageAdapter.listener = listener
            adapter = actorImageAdapter
        }
    }

    private fun initRecyclerMovies() {
        val context = recyclerViewActorMovies.context

        recyclerViewActorMovies.apply {
            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object : ActorMovieAdapter.Listener {
                override fun onItemClicked(movie: Movie) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)

                    intent.putExtra(MOVIE_ID, movie.movieOrSerialId)

                    startActivity(intent)
                }
            }

            actorMovieAdapter.listener = listener
            adapter = actorMovieAdapter
        }
    }

    override fun onDownloadResultDetails(actor: Actor) {
        ImageLoader.getInstance()?.load(actor.image, WeakReference(posterImage))
        collapsingToolbar.title = actor.actorName
        actorBiography.text = actor.biography
    }

    override fun onDownloadImageURLs(images: List<String>) {
        actorImageAdapter.setItems(images)
    }

    override fun onDownloadActorMovies(movies: List<Movie>) {
        actorMovieAdapter.setItems(movies)
    }

    override fun onDownloadDetailsError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        actorBiography.visibility = View.GONE
        recyclerViewActorImages.visibility = View.GONE
        actorMoviesText.visibility = View.GONE
        recyclerViewActorMovies.visibility = View.GONE
        collapsingToolbar.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        actorBiography.visibility = View.VISIBLE
        recyclerViewActorImages.visibility = View.VISIBLE
        actorMoviesText.visibility = View.VISIBLE
        recyclerViewActorMovies.visibility = View.VISIBLE
        collapsingToolbar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        actorPresenter.onDestroy()
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}