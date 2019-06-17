package com.example.android.movie.ui.main.topmovies

import android.widget.Toast
import com.example.android.movie.App
import com.example.android.movie.mvp.topmovies.ITopMoviesPresenter
import com.example.android.movie.mvp.topmovies.ITopMoviesView
import com.example.android.network.NetworkRepositoryManager
import com.example.android.network.NetworkCallback
import com.example.android.network.models.movie.MovieList

class TopMoviesPresenter(private var topMoviesView: ITopMoviesView?) :
    ITopMoviesPresenter {

    override fun onDownloadMovies(page: Int) {

        NetworkRepositoryManager.getMoviesRepositoryImpl().loadPopularMovies(page, object :
            NetworkCallback<MovieList> {
            override fun onSuccess(result: MovieList) {
                if (page != result.totalPages) {
                    topMoviesView?.onDownloadResult(result)
                    topMoviesView?.hideLoading()
                } else {
                    Toast.makeText(App.get(), "All items", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: String) {
                topMoviesView?.onDownloadError(error)
                topMoviesView?.hideLoading()
            }
        })
    }

    override fun onSearchMovies(query: String?) {

        val userInput = query?.toLowerCase()

        query?.let {
            if (it.length >= 3) {
                NetworkRepositoryManager.getMoviesRepositoryImpl()
                    .searchMovie(query, object :
                        NetworkCallback<MovieList> {
                        override fun onSuccess(result: MovieList) {
                            for (movie in result.results) {
                                if (movie.title?.toLowerCase()!!.contains(userInput.toString())) {
                                    topMoviesView?.onSearchResult(result)
                                }
                            }
                        }

                        override fun onError(error: String) {
                            topMoviesView?.onDownloadError(error)
                        }
                    })
            }
        }
    }

    override fun onDestroy() {
        topMoviesView = null
    }

}