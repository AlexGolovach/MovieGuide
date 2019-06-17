package com.example.android.movie.ui.main.details.serial

import ImageLoader
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video
import com.example.android.movie.R
import com.example.android.movie.mvp.serialdetails.ISerialDetailsPresenter
import com.example.android.movie.mvp.serialdetails.ISerialDetailsView
import com.example.android.movie.ui.base.BaseFragment
import com.example.android.movie.ui.main.details.PlayerFragment
import com.example.android.movie.ui.main.details.actor.ActorFragment
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.Constants.ACTOR_ID
import com.example.android.movie.ui.utils.Constants.SERIAL
import com.example.android.movie.ui.utils.Constants.SERIAL_ID
import com.example.android.movie.ui.utils.Constants.VIDEO_ID
import com.example.android.movie.ui.utils.getBundleWithId
import com.example.android.movie.ui.utils.getBundleWithVideo
import kotlinx.android.synthetic.main.fragment_serial_details.*
import java.lang.ref.WeakReference

class SerialDetailsFragment : BaseFragment(), ISerialDetailsView,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var serialDetailsPresenter: ISerialDetailsPresenter

    private lateinit var serialActorsAdapter: SerialActorsAdapter
    private lateinit var recommendedSerialsAdapter: RecommendedSerialsAdapter
    private lateinit var serialVideosAdapter: SerialVideosAdapter

    private var serialId: Int? = null
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_serial_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serialActorsAdapter =
            SerialActorsAdapter()
        recommendedSerialsAdapter =
            RecommendedSerialsAdapter()
        serialVideosAdapter =
            SerialVideosAdapter()

        serialDetailsPresenter =
            SerialDetailsPresenter(isOnline(), this)

        handler = Handler()

        swipeRefreshLayout.setOnRefreshListener(this)

        getData()
        initRecyclerActors()
        initRecyclerRecommendedSerials()
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
        serialId = arguments?.getInt(SERIAL_ID)

        serialId?.let { id ->
            serialDetailsPresenter.apply {
                checkSerialInFavorites(AccountOperation.getAccount().id, id)
                onDownloadSerialDetails(id)
                onDownloadActorSquad(id)
                onDownloadRecommendedSerials(id)
                onDownloadVideo(id)
            }
        }
    }

    override fun isSerialInFavorites(result: Boolean) {
        initToolbar(result)
    }

    private fun initToolbar(isFavorite: Boolean) {
        serialId = arguments?.getInt(SERIAL_ID)

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
                        serialId?.let { id ->
                            serialDetailsPresenter.addToFavorite(id, SERIAL)
                            serialDetailsPresenter.checkSerialInFavorites(
                                AccountOperation.getAccount().id,
                                id
                            )
                        }

                        it.isVisible = false
                        menu.findItem(R.id.deleteFromFavorite).isVisible = true

                        true
                    }
                    R.id.deleteFromFavorite -> {
                        serialId?.let { id ->
                            serialDetailsPresenter.deleteFromFavorite(
                                AccountOperation.getAccount().id,
                                id
                            )
                            serialDetailsPresenter.checkSerialInFavorites(
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

            val listener = object :
                SerialActorsAdapter.Listener {
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

            serialActorsAdapter.listener = listener
            adapter = serialActorsAdapter
        }
    }

    private fun initRecyclerRecommendedSerials() {
        val context = recyclerViewRecommendedShows.context

        recyclerViewRecommendedShows.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object :
                RecommendedSerialsAdapter.Listener {
                override fun onItemClicked(serial: Movie) {
                    val serialDetailsFragment =
                        SerialDetailsFragment()
                    serialDetailsFragment.arguments =
                        getBundleWithId(SERIAL_ID, serial.movieOrSerialId)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(serial.title)
                        ?.replace(
                            R.id.container,
                            serialDetailsFragment,
                            serial.title
                        )
                        ?.commit()
                }
            }

            recommendedSerialsAdapter.listener = listener
            adapter = recommendedSerialsAdapter
        }
    }

    private fun initRecyclerVideos() {
        val context = recyclerViewVideos.context

        recyclerViewVideos.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            val listener = object : SerialVideosAdapter.OnClickListener {
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

            serialVideosAdapter.listener = listener

            adapter = serialVideosAdapter
        }
    }

    override fun onDownloadResultDetails(serial: Details) {
        releaseDateText.text = serial.date
        runtimeText.text = serial.runtime
        showDescriptionText.text = serial.description
        ImageLoader.getInstance()?.load(serial.image, WeakReference(posterImage))
        collapsingToolbar.title = serial.title
    }

    override fun onDownloadActorSquad(actorSquad: List<ActorSquad>) {
        serialActorsAdapter.setItems(actorSquad)
    }

    override fun onDownloadRecommendedSerials(serials: List<Movie>) {
        recommendedSerialsAdapter.setItems(serials)
    }

    override fun onDownloadVideo(videos: List<Video>) {
        if (videos.isEmpty()) {
            videoText.visibility = View.GONE
        } else {
            serialVideosAdapter.setItems(videos)
        }
    }

    override fun onDownloadDetailsError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        collapsingToolbar.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        detailsLayout.visibility = View.VISIBLE
        collapsingToolbar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        serialDetailsPresenter.onDestroy()
        handler.removeCallbacksAndMessages(null)

        super.onDestroyView()
    }
}