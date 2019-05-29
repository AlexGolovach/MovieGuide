package com.example.android.movie.ui.main.information.details.movie

import com.example.android.database.Callback
import com.example.android.database.model.Reviews
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.network.Injector
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.MovieActorSquad
import com.example.android.network.models.video.VideosList
import com.example.android.network.repository.actors.ActorsCallback
import com.example.android.network.repository.movies.MoviesCallback
import com.example.android.network.repository.videos.VideosCallback

class MovieDetailsPresenter(private var iMovieDetailsView: IMovieDetailsView?) :
    IMovieDetailsPresenter {

    private var youTubeVideos = mutableListOf<String>()

    override fun onDownloadMovieDetails(movieId: Int) {
        iMovieDetailsView?.showLoading()

        Injector.getMoviesRepositoryImpl()
            .getInformationAboutMovie(movieId, object :
                MoviesCallback<MovieDetails> {
                override fun onSuccess(result: MovieDetails) {
                    iMovieDetailsView?.onDownloadResultDetails(result)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    override fun onDownloadActorSquad(movieId: Int) {
        Injector.getActorsRepositoryImpl()
            .getMovieActorsSquad(movieId, object : ActorsCallback<MovieActorSquad> {
                override fun onSuccess(result: MovieActorSquad) {
                    iMovieDetailsView?.onDownloadActorSquad(result)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    override fun onDownloadRecommendedMovies(movieId: Int) {
        Injector.getMoviesRepositoryImpl()
            .getRecommendedMoviesForMovie(movieId, object : MoviesCallback<MovieList> {
                override fun onSuccess(result: MovieList) {
                    iMovieDetailsView?.onDownloadRecommendedMovies(result)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    override fun onDownloadVideo(movieId: Int) {
        Injector.getVideosRepositoryImpl().getVideosForMovie(movieId, object : VideosCallback {
            override fun onSuccess(result: VideosList) {
                youTubeVideos.clear()

                for (i in 0 until result.results.size) {
                    youTubeVideos.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${result.results[i].key}\" frameborder=\"0\" allowfullscreen></iframe>")
                }

                iMovieDetailsView?.onDownloadVideo(youTubeVideos)
                iMovieDetailsView?.hideLoading()
            }

            override fun onError(error: Throwable) {
                iMovieDetailsView?.hideLoading()
            }
        })
    }

    override fun addMovieToFavorites(movieId: Int) {
        Injector.getMoviesRepositoryImpl()
            .getInformationAboutMovie(movieId, object : MoviesCallback<MovieDetails> {
                override fun onSuccess(result: MovieDetails) {
                    com.example.android.database.Injector.getFavoriteMoviesRepositoryImpl()
                        .addMovieInFavorites(AccountOperation.getAccount().id, result)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                }
            })
    }

    override fun checkMovieInFavorites(movieId: Int) {
        com.example.android.database.Injector.getFavoriteMoviesRepositoryImpl()
            .checkMovieInFavorites(movieId, object :
                Callback<Boolean> {
                override fun onSuccess(result: Boolean) {
                    iMovieDetailsView?.isMovieAddedInFavorites(result)
                }

                override fun onError(throwable: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }

    override fun addReviewForFilm(movieId: Int, userName: String, review: String) {
        com.example.android.database.Injector.getReviewsRepositoryImpl()
            .addReviewForFilm(movieId, userName, review)
    }

    override fun loadReviewForFilm(movieId: Int) {
        com.example.android.database.Injector.getReviewsRepositoryImpl()
            .loadReviewsForFilm(movieId, object : Callback<ArrayList<Reviews>> {
                override fun onSuccess(result: ArrayList<Reviews>) {
                    iMovieDetailsView?.onDownloadReviewsForMovie(result)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                }
            })
    }

    override fun deleteReviewForFilm(documentId: String) {
        com.example.android.database.Injector.getReviewsRepositoryImpl().deleteReview(documentId)
    }

    override fun onDestroy() {
        iMovieDetailsView = null
    }

}