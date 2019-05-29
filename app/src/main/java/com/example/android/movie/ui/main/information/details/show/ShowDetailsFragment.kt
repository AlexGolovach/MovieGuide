package com.example.android.movie.ui.main.information.details.show

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
import com.example.android.movie.mvp.showdetails.IShowDetailsPresenter
import com.example.android.movie.mvp.showdetails.IShowDetailsView
import com.example.android.movie.ui.main.information.actor.ActorFragment
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.convertTime
import com.example.android.movie.ui.utils.getBundleWithId
import com.example.android.network.Converter
import com.example.android.network.models.recommendedshows.RecommendShows
import com.example.android.network.models.recommendedshows.RecommendShowsList
import com.example.android.network.models.showdetails.ShowDetails
import com.example.android.network.models.showsquad.Cast
import com.example.android.network.models.showsquad.ActorShowSquad
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_show_details.*

class ShowDetailsFragment : Fragment(), IShowDetailsView {

    private lateinit var showDetailsPresenter: IShowDetailsPresenter

    private lateinit var showActorsAdapter: ShowActorsAdapter
    private lateinit var recommendedShowsAdapter: RecommendedShowsAdapter
    private lateinit var showVideosAdapter: ShowVideosAdapter
    private lateinit var showReviewsAdapter: ShowReviewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_show_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showDetailsPresenter =
            ShowDetailsPresenter(this)

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
        val showId = arguments?.getInt("SHOW_ID")

        showId?.let { id ->
            showDetailsPresenter.checkShowInFavorites(showId)
            showDetailsPresenter.onDownloadShowDetails(id)
            showDetailsPresenter.onDownloadActorSquad(id)
            showDetailsPresenter.onDownloadRecommendedShows(id)
            showDetailsPresenter.onDownloadVideo(id)
            showDetailsPresenter.loadReviewForShow(showId)

            icon_send_review.setOnClickListener {
                writeReview(showId)
            }

            icon_like.setOnClickListener {
                icon_like.setImageResource(R.drawable.ic_check)
                icon_like.isClickable = false
                showDetailsPresenter.addShowToFavorites(id)
            }
        }
    }

    private fun initRecyclerActors() {
        val context = recycler_view_actor.context

        showActorsAdapter =
            ShowActorsAdapter()

        recycler_view_actor.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.HORIZONTAL))

            val listener = object :
                ShowActorsAdapter.Listener {
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

    private fun initRecyclerRecommendedMovies() {
        val context = recycler_view_recommended_shows.context

        recommendedShowsAdapter =
            RecommendedShowsAdapter()

        recycler_view_recommended_shows.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)

            val listener = object :
                RecommendedShowsAdapter.Listener {
                override fun onItemClicked(show: RecommendShows) {
                    val showDetailsFragment =
                        ShowDetailsFragment()
                    showDetailsFragment.arguments =
                        getBundleWithId("SHOW_ID", show.id)

                    fragmentManager?.beginTransaction()
                        ?.addToBackStack(show.title)
                        ?.replace(
                            R.id.container,
                            showDetailsFragment,
                            show.title
                        )
                        ?.commit()
                }
            }

            recommendedShowsAdapter.listener = listener
            adapter = recommendedShowsAdapter
        }
    }

    private fun initRecyclerVideos() {
        val context = recycler_view_videos.context

        showVideosAdapter =
            ShowVideosAdapter()

        recycler_view_videos.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            adapter = showVideosAdapter
        }
    }

    private fun initRecyclerReviews() {
        val context = recycler_view_reviews.context

        showReviewsAdapter =
            ShowReviewsAdapter()

        recycler_view_reviews.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val listener = object :
                ShowReviewsAdapter.DeleteListener {
                override fun onDeleteClickedListener(reviews: Reviews) {
                    showDetailsPresenter.deleteReviewForShow(reviews.documentId)
                }
            }

            showReviewsAdapter.deleteListener = listener
            adapter = showReviewsAdapter
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

    private fun writeReview(showId: Int) {
        val review = write_review_edit_text.text.toString()

        if (!TextUtils.isEmpty(review)) {

            showDetailsPresenter.addReviewForShow(
                showId,
                AccountOperation.getAccount().login,
                review
            )

            write_review_edit_text.text = null

            showDetailsPresenter.loadReviewForShow(showId)
        }
    }

    override fun onDownloadResultDetails(show: ShowDetails) {
        val imageUrl = show.image?.let { Converter.getImageUrl(it) }

        release_date_text.text = show.release_date
        runtime_text.text = convertTime(show.episode_time[0])
        show_description_text.text = show.overview

        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
            .into(poster_image)

        collapsing_toolbar.title = show.name

    }

    override fun onDownloadActorSquad(actorSquad: ActorShowSquad) {
        showActorsAdapter.setItems(actorSquad)
    }

    override fun onDownloadRecommendedShows(shows: RecommendShowsList) {
        recommendedShowsAdapter.setItems(shows)
    }

    override fun onDownloadVideo(videos: List<String>) {
        showVideosAdapter.setItems(videos)
    }

    override fun onDownloadReviewsForShow(result: ArrayList<Reviews>) {
        showReviewsAdapter.updateItems(result)
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
        showDetailsPresenter.onDestroy()
        super.onDestroy()
    }
}