package com.example.android.movie.ui.favorites.movies

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.FavoriteMovies
import com.example.android.movie.R
import com.example.android.movie.mvp.favoritemovies.IFavoriteMoviesPresenter
import com.example.android.movie.mvp.favoritemovies.IFavoriteMoviesView
import com.example.android.movie.ui.main.information.MovieDetailsActivity
import com.example.android.movie.ui.utils.AccountOperation
import kotlinx.android.synthetic.main.fragment_favorite_movies.*
import java.lang.NullPointerException

class FavoriteMoviesFragment : Fragment(), IFavoriteMoviesView {

    private lateinit var favoritesPresenter: IFavoriteMoviesPresenter
    private lateinit var favoritesAdapter: FavoriteMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorite_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesPresenter = FavoriteMoviesPresenter(this)
        favoritesPresenter.downloadMyFavorites(AccountOperation.getAccount().id)

        initRecycler()
    }

    private fun initRecycler() {
        val context = favorites_recycler_view.context

        favoritesAdapter = FavoriteMoviesAdapter()

        favorites_recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val openListener = object : FavoriteMoviesAdapter.OpenListener {
                override fun onOpenClikedListener(movie: FavoriteMovies) {
                    val intent = Intent(activity, MovieDetailsActivity::class.java)
                    intent.putExtra("MOVIE_ID", movie.movieId)

                    startActivity(intent)
                }
            }

            val deleteListener = object : FavoriteMoviesAdapter.DeleteListener {
                override fun onDeleteClickedListener(movie: FavoriteMovies) {
                    favoritesPresenter.deleteMovie(movie)
                    favoritesAdapter.deleteItems(movie)
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

    override fun onDownloadFavorites(result: ArrayList<FavoriteMovies>) {
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
        if (throwable is NullPointerException) {
            progress_bar.visibility = View.GONE
            empty_favorites.visibility = View.VISIBLE
        }
    }

}