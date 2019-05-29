package com.example.android.movie.ui.main.topshows

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
import com.example.android.movie.mvp.topshows.ITopShowsPresenter
import com.example.android.movie.mvp.topshows.ITopShowsView
import com.example.android.movie.ui.main.information.ShowDetailsActivity
import com.example.android.network.models.shows.Show
import com.example.android.network.models.shows.ShowsList
import kotlinx.android.synthetic.main.fragment_top_shows.*

class TopShowsFragment : Fragment(), ITopShowsView {

    private lateinit var topShowsPresenter: ITopShowsPresenter
    private lateinit var topAdapter: TopShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_top_shows, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topAdapter = TopShowsAdapter()
        topShowsPresenter = TopShowsPresenter(this)

        getData()
        initRecycler()
    }

    private fun getData() {
        topShowsPresenter.onDownloadShows()
    }

    private fun initRecycler() {
        val context = recycler_view.context

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val listener = object : TopShowsAdapter.Listener {
                override fun onItemClicked(show: Show) {
                    val intent = Intent(activity, ShowDetailsActivity::class.java)

                    intent.putExtra("SHOW_ID", show.id)

                    startActivity(intent)
                }
            }

            topAdapter.listener = listener
            adapter = topAdapter
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onDownloadResult(shows: ShowsList) {
        topAdapter.setItems(shows)
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

    override fun onDestroy() {
        topShowsPresenter.onDestroy()

        super.onDestroy()
    }
}