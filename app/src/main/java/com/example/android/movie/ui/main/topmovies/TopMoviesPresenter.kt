package com.example.android.movie.ui.main.topmovies

import com.example.android.movie.mvp.topmovies.ITopMoviesPresenter
import com.example.android.movie.mvp.topmovies.ITopMoviesView
import com.example.android.network.Injector
import com.example.android.network.NetworkCallback
import com.example.android.network.models.movie.MovieList

class TopMoviesPresenter(private var topMoviesView: ITopMoviesView?) :
    ITopMoviesPresenter {

    override fun onDownloadMovies() {

        Injector.getMoviesRepositoryImpl().loadPopularMovies(object :
            NetworkCallback<MovieList> {
            override fun onSuccess(result: MovieList) {
                topMoviesView?.onDownloadResult(result)
                topMoviesView?.hideLoading()
            }

            override fun onError(throwable: Throwable) {
                topMoviesView?.onDownloadError(throwable)
                topMoviesView?.showLoading()
            }
        })
    }

    override fun onSearchMovies(query: String?) {

        val userInput = query?.toLowerCase()

        query?.let {
            if (it.length >= 3) {
                Injector.getMoviesRepositoryImpl()
                    .searchMovie(query, object :
                        NetworkCallback<MovieList> {
                        override fun onSuccess(result: MovieList) {
                            for (movie in result.results) {
                                if (movie.title?.toLowerCase()!!.contains(userInput.toString())) {
                                    topMoviesView?.onSearchResult(result)
                                }
                            }
                        }

                        override fun onError(throwable: Throwable) {
                            topMoviesView?.onDownloadError(throwable)
                        }
                    })
            }
        }
    }

    override fun onDestroy() {
        topMoviesView = null
    }

}