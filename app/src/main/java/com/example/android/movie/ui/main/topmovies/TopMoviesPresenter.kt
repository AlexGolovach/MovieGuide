package com.example.android.movie.ui.main.topmovies

import com.example.android.movie.mvp.topmovies.ITopMoviesPresenter
import com.example.android.movie.mvp.topmovies.ITopMoviesView
import com.example.android.network.Injector
import com.example.android.network.models.movie.MovieList
import com.example.android.network.repository.movies.MoviesCallback

class TopMoviesPresenter(private var topMoviesView: ITopMoviesView?) :
    ITopMoviesPresenter {

    override fun onDownloadMovies() {
        topMoviesView?.showLoading()

        Injector.getMoviesRepositoryImpl().loadPopularMovies(object :
            MoviesCallback<MovieList> {
            override fun onSuccess(result: MovieList) {
                topMoviesView?.onDownloadResult(result)
                topMoviesView?.hideLoading()
            }

            override fun onError(throwable: Throwable) {
                topMoviesView?.onDownloadError(throwable)
                topMoviesView?.hideLoading()
            }
        })
    }

    override fun onDestroy(){
        topMoviesView = null
    }
}