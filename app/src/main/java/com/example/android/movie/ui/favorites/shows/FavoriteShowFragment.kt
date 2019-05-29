package com.example.android.movie.ui.favorites.shows

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.FavoriteShows
import com.example.android.movie.R
import com.example.android.movie.mvp.favoriteshows.IFavoriteShowsPresenter
import com.example.android.movie.mvp.favoriteshows.IFavoriteShowsView
import com.example.android.movie.ui.main.information.ShowDetailsActivity
import com.example.android.movie.ui.utils.AccountOperation
import kotlinx.android.synthetic.main.fragment_favorite_shows.*

class FavoriteShowFragment : Fragment(), IFavoriteShowsView {

    private lateinit var favoritesPresenter: IFavoriteShowsPresenter
    private lateinit var favoritesAdapter: FavoriteShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorite_shows, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesPresenter = FavoriteShowsPresenter(this)
        favoritesPresenter.downloadMyFavoriteShows(AccountOperation.getAccount().id)

        initRecycler()
    }

    private fun initRecycler() {
        val context = favorites_recycler_view.context

        favoritesAdapter = FavoriteShowsAdapter()

        favorites_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val openListener = object : FavoriteShowsAdapter.OpenListener {
                override fun onOpenClikedListener(show: FavoriteShows) {
                    val intent = Intent(activity, ShowDetailsActivity::class.java)
                    intent.putExtra("SHOW_ID", show.showId)

                    startActivity(intent)
                }
            }

            val deleteListener = object : FavoriteShowsAdapter.DeleteListener {
                override fun onDeleteClickedListener(show: FavoriteShows) {
                    favoritesPresenter.deleteShow(show)
                    favoritesAdapter.deleteItems(show)
                }
            }

            favoritesAdapter.openListener = openListener
            favoritesAdapter.deleteListener = deleteListener

            adapter = favoritesAdapter
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
        favorites_recycler_view.visibility = View.VISIBLE
    }

    override fun onDownloadFavoriteShows(result: ArrayList<FavoriteShows>) {
        if (result.size == 0) {
            progress_bar.visibility = View.GONE
            favorites_recycler_view.visibility = View.GONE
            empty_favorites.visibility = View.VISIBLE
        } else {
            favoritesAdapter.setItems(result)
        }
    }

    override fun deleteSuccess(success: String) {
        Toast.makeText(activity, success, Toast.LENGTH_SHORT).show()
    }

    override fun deleteError(error: Throwable) {
        if (error is Exception) {
            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDownloadFavoritesError(throwable: Throwable) {
        progress_bar.visibility = View.GONE
        empty_favorites.visibility = View.VISIBLE
    }

}