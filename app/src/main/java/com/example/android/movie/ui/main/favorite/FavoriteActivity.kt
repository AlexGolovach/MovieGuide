package com.example.android.movie.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.View
import android.widget.Toast
import com.example.android.database.model.Favorite
import com.example.android.movie.ui.base.BaseActivity
import com.example.android.movie.R
import com.example.android.movie.mvp.favorite.IFavoritePresenter
import com.example.android.movie.mvp.favorite.IFavoriteView
import com.example.android.movie.ui.main.details.MovieDetailsActivity
import com.example.android.movie.ui.main.details.SerialDetailsActivity
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.Constants.FILM
import com.example.android.movie.ui.utils.Constants.MOVIE_ID
import com.example.android.movie.ui.utils.Constants.SERIAL
import com.example.android.movie.ui.utils.Constants.SERIAL_ID
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : BaseActivity(), IFavoriteView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var favoritePresenter: IFavoritePresenter
    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar)

        favoritePresenter = FavoritePresenter(this)
        favoriteAdapter = FavoriteAdapter()

        handler = Handler()

        swipeRefreshLayout.setOnRefreshListener(this)

        initToolbar()

        getData()
        initRecycler()
    }

    private fun initToolbar() {
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun onRefresh() {
        handler.postDelayed({
            swipeRefreshLayout.isRefreshing = false
            getData()
        }, 2000)
    }

    private fun getData() {
        favoritePresenter.loadFavorites(AccountOperation.getAccount().id)
    }

    private fun initRecycler() {
        val context = recyclerViewFavorite.context

        recyclerViewFavorite.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val clickListener = object : FavoriteAdapter.OnClickListener {
                override fun onItemClicked(favorite: Favorite) {
                    when (favorite.type) {
                        SERIAL -> {
                            val intent =
                                Intent(this@FavoriteActivity, SerialDetailsActivity::class.java)

                            intent.putExtra(SERIAL_ID, favorite.movieOrSerialId)

                            startActivity(intent)
                        }
                        FILM -> {
                            val intent =
                                Intent(this@FavoriteActivity, MovieDetailsActivity::class.java)

                            intent.putExtra(MOVIE_ID, favorite.movieOrSerialId)

                            startActivity(intent)
                        }
                    }
                }
            }

            val deleteListener = object : FavoriteAdapter.OnDeleteListener {
                override fun onDeleteClicked(favorite: Favorite) {
                    favoriteAdapter.deleteItems(favorite)

                    favoritePresenter.deleteFromFavorite(
                        AccountOperation.getAccount().id,
                        favorite.movieOrSerialId
                    )
                }
            }

            favoriteAdapter.clickListener = clickListener
            favoriteAdapter.deleteListener = deleteListener

            adapter = favoriteAdapter
        }
    }

    override fun onDownloadFavorites(result: ArrayList<Favorite>) {
        if (result.size == 0) {
            noItemsText.visibility = View.VISIBLE
        } else {
            favoriteAdapter.setItems(result)
        }
    }

    override fun onDownloadError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerViewFavorite.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerViewFavorite.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        favoritePresenter.onDestroy()
        handler.removeCallbacksAndMessages(null)

        super.onDestroy()
    }
}