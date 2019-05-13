package com.example.android.movie.ui.main.topmovies

import com.example.android.movie.mvp.topmovies.ITopMoviesPresenter
import com.example.android.movie.mvp.topmovies.ITopMoviesView
import com.example.android.network.Injector
import com.example.android.network.models.movie.Movie
import com.example.android.network.repository.movies.MoviesCallback

class TopMoviesPresenter(private var topMoviesView: ITopMoviesView?) :
    ITopMoviesPresenter {

    override fun onDownloadMovies() {

        Injector.getMoviesRepositoryImpl().loadMovies(object :
            MoviesCallback<List<Movie>> {
            override fun onSuccess(result: List<Movie>) {
                topMoviesView?.onDownloadResult(result)
                topMoviesView?.hideLoading()
            }

            override fun onError(throwable: Throwable) {
                topMoviesView?.onDownloadError(throwable)
                topMoviesView?.showLoading()
            }
        })
    }

    override fun onDestroy(){
        topMoviesView = null
    }

}