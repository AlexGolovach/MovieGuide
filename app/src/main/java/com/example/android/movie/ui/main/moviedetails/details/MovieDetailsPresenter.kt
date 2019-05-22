package com.example.android.movie.ui.main.moviedetails.details

import ImageLoader
import android.graphics.Bitmap
import com.example.android.imageloader.Callback
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.Injector
import com.example.android.network.models.movievideo.MovieVideos
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.MovieActorSquad
import com.example.android.network.repository.actors.ActorsCallback
import com.example.android.network.repository.movies.MoviesCallback
import com.example.android.network.repository.videos.VideosCallback

class MovieDetailsPresenter(private var iMovieDetailsView: IMovieDetailsView?) :
    IMovieDetailsPresenter {

    private var youTubeVideos = mutableListOf<String>()

    override fun onDownloadMovieDetails(movieId: Int) {

        Injector.getMoviesRepositoryImpl()
            .getInformationAboutMovie(movieId, object :
                MoviesCallback<MovieDetails> {
                override fun onSuccess(result: MovieDetails) {

                    val imageUrl = result.image?.let { getImageUrl(it) }

                    imageUrl?.let {
                        ImageLoader.getInstance()?.load(it, object : Callback {
                            override fun onSuccess(url: String, bitmap: Bitmap) {
                                iMovieDetailsView?.onDownloadResultDetails(result, bitmap)
                            }

                            override fun onError(url: String, throwable: Throwable) {
                                iMovieDetailsView?.onDownloadDetailsError(throwable)
                            }
                        })
                    }
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.showLoading()
                }
            })
    }

    override fun onDownloadActorSquad(movieId: Int) {
        Injector.getActorsRepositoryImpl()
            .getActorsSquad(movieId, object : ActorsCallback<MovieActorSquad> {
                override fun onSuccess(result: MovieActorSquad) {
                    iMovieDetailsView?.onDownloadActorSquad(result)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.showLoading()
                }
            })
    }

    override fun onDownloadRecommendedMovies(movieId: Int) {
        Injector.getMoviesRepositoryImpl()
            .getRecommendedMoviesForMovie(movieId, object : MoviesCallback<MovieList> {
                override fun onSuccess(result: MovieList) {
                    iMovieDetailsView?.onDownloadRecommendedMovies(result)
                    iMovieDetailsView?.hideLoading()
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.showLoading()
                }
            })
    }

    override fun onDownloadVideo(movieId: Int) {
        Injector.getVideosRepositoryImpl().getVideosForMovie(movieId, object : VideosCallback {
            override fun onSuccess(result: MovieVideos) {
                youTubeVideos.clear()

                for (i in 0 until result.results.size) {
                    youTubeVideos.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${result.results[i].key}\" frameborder=\"0\" allowfullscreen></iframe>")
                }

                iMovieDetailsView?.onDownloadVideo(youTubeVideos)
                iMovieDetailsView?.hideLoading()
            }

            override fun onError(error: Throwable) {
                iMovieDetailsView?.showLoading()
            }
        })
    }

    override fun onDestroy() {
        iMovieDetailsView = null
    }

}