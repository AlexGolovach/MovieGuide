package com.example.android.movie.ui.main.moviedetails

import ImageLoader
import android.graphics.Bitmap
import com.example.android.imageloader.Callback
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.network.Injector
import com.example.android.network.getImageUrl
import com.example.android.network.models.movie.Movie
import com.example.android.network.models.movie.MovieActorSquad
import com.example.android.network.models.movie.MovieDetails
import com.example.android.network.repository.actors.ActorsCallback
import com.example.android.network.repository.movies.MoviesCallback

class MovieDetailsPresenter(private var iMovieDetailsView: IMovieDetailsView?) :
    IMovieDetailsPresenter {

    override fun onDownloadMovieDetails(movieId: Long) {

        Injector.getMoviesRepositoryImpl()
            .getInformationAboutMovie(movieId, object :
                MoviesCallback<MovieDetails> {
                override fun onSuccess(result: MovieDetails) {

                    val imageUrl = getImageUrl(result.movie.image)

                    ImageLoader.getInstance()?.load(imageUrl, object : Callback {
                        override fun onSuccess(url: String, bitmap: Bitmap) {

                            iMovieDetailsView?.onDownloadResultDetails(result, bitmap)
                            iMovieDetailsView?.hideLoading()
                        }

                        override fun onError(url: String, throwable: Throwable) {
                            iMovieDetailsView?.showLoading()
                        }
                    })
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.showLoading()
                }
            })
    }

    override fun onDownloadActorSquad(movieId: Long) {
        Injector.getActorsRepositoryImpl()
            .getActorsSquad(movieId, object : ActorsCallback<List<MovieActorSquad>> {
                override fun onSuccess(result: List<MovieActorSquad>) {
                    iMovieDetailsView?.onDownloadActorSquad(result)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                }
            })
    }

    override fun onDownloadRecommendedMovies(movieId: Long) {
        Injector.getMoviesRepositoryImpl().getRecommendedMoviesForMovie(movieId, object : MoviesCallback<List<Movie>>{
            override fun onSuccess(result: List<Movie>) {
                iMovieDetailsView?.onDownloadRecommendedMovies(result)
            }

            override fun onError(throwable: Throwable) {
                iMovieDetailsView?.onDownloadDetailsError(throwable)
            }
        })
    }

    override fun onDestroy() {
        iMovieDetailsView = null
    }

}