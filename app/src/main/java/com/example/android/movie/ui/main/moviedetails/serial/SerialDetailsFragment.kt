package com.example.android.movie.ui.main.moviedetails.serial

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.imageloader.Callback
import com.example.android.movie.R
import com.example.android.movie.mvp.serialdetails.ISerialDetailsPresenter
import com.example.android.movie.mvp.serialdetails.ISerialDetailsView
import com.example.android.movie.ui.main.moviedetails.actor.ActorFragment
import com.example.android.movie.ui.utils.convertTime
import com.example.android.movie.ui.utils.getBundleWithId
import com.example.android.network.Converter
import com.example.android.network.models.SerialDetails
import com.example.android.network.models.recommendedserials.RecommendSerials
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serialsquad.Cast
import com.example.android.network.models.serialsquad.SerialActorSquad
import kotlinx.android.synthetic.main.fragment_serial_details.*

class SerialDetailsFragment : Fragment(), ISerialDetailsView {

    private lateinit var serialDetailsPresenter: ISerialDetailsPresenter

    private lateinit var showActorsAdapter: SerialActorsAdapter
    private lateinit var recommendedSerialsAdapter: RecommendedSerialsAdapter
    private lateinit var serialVideosAdapter: SerialVideosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_serial_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        serialDetailsPresenter =
            SerialDetailsPresenter(this)

        getData()

        initToolbar()
        initRecyclerActors()
        initRecyclerRecommendedSerials()
        initRecyclerVideos()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        collapsing_toolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getData() {
        val showId = arguments?.getInt("SERIAL_ID")

        showId?.let { id ->
            serialDetailsPresenter.onDownloadSerialDetails(id)
            serialDetailsPresenter.onDownloadActorSquad(id)
            serialDetailsPresenter.onDownloadRecommendedSerials(id)
            serialDetailsPresenter.onDownloadVideo(id)
        }
    }

    private fun initRecyclerActors() {
        val context = recycler_view_actor.context

        showActorsAdapter =
            SerialActorsAdapter()

        recycler_view_actor.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))

            val listener = object :
                SerialActorsAdapter.Listener {
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

            showActorsAdapter.listener = listener
            adapter = showActorsAdapter
        }
    }

    private fun initRecyclerRecommendedSerials() {
        val context = recycler_view_recommended_shows.context

        recommendedSerialsAdapter =
            RecommendedSerialsAdapter()

        recycler_view_recommended_shows.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object :
                RecommendedSerialsAdapter.Listener {
                override fun onItemClicked(serial: RecommendSerials) {
                    val serialDetailsFragment =
                        SerialDetailsFragment()
                    serialDetailsFragment.arguments =
                        getBundleWithId("SERIAL_ID", serial.id)

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
        val context = recycler_view_videos.context

        serialVideosAdapter =
            SerialVideosAdapter()

        recycler_view_videos.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            adapter = serialVideosAdapter
        }
    }

    override fun onDownloadResultDetails(serial: SerialDetails) {
        val imageUrl = serial.image?.let { Converter.getImageUrl(it) }

        release_date_text.text = serial.release_date
        runtime_text.text = convertTime(serial.episode_time[0])
        show_description_text.text = serial.overview

        imageUrl?.let {
            ImageLoader.getInstance()?.load(it, object : Callback {
                override fun onSuccess(url: String, bitmap: Bitmap) {
                    if (imageUrl == url) {
                        poster_image.background = null
                        poster_image.setImageBitmap(bitmap)
                    }
                }

                override fun onError(url: String, throwable: Throwable) {
                    if (imageUrl == url) {
                        poster_image.setImageResource(R.drawable.actor_placeholder)
                    }
                }
            })
        }

        collapsing_toolbar.title = serial.name

    }

    override fun onDownloadActorSquad(actorSquad: SerialActorSquad) {
        showActorsAdapter.setItems(actorSquad)
    }

    override fun onDownloadRecommendedSerials(serials: RecommendSerialsList) {
        recommendedSerialsAdapter.setItems(serials)
    }

    override fun onDownloadVideo(videos: List<String>) {
        serialVideosAdapter.setItems(videos)
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
        serialDetailsPresenter.onDestroy()
        super.onDestroy()
    }
}