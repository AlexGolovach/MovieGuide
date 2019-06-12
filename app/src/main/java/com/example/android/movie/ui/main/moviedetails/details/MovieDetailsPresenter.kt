package com.example.android.movie.ui.main.moviedetails.details

import ImageLoader
import android.graphics.Bitmap
import android.util.Log
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Information
import com.example.android.database.model.Movie
import com.example.android.imageloader.Callback
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.movie.ui.utils.convertTime
import com.example.android.network.Converter.Companion.getImageUrl
import com.example.android.network.Injector
import com.example.android.network.NetworkCallback
import com.example.android.network.models.VideoList
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.Cast
import com.example.android.network.models.moviesquad.MovieActorSquad

class MovieDetailsPresenter(private var iMovieDetailsView: IMovieDetailsView?) :
    IMovieDetailsPresenter {

    private var youTubeVideos = mutableListOf<String>()

    override fun onDownloadMovieDetails(movieId: Int) {
        iMovieDetailsView?.showLoading()

        com.example.android.database.Injector.getDBRepositoryImpl()
            .loadInformation(movieId, object : com.example.android.database.Callback<Information> {
                override fun onSuccess(result: Information) {

                    result.image?.let {
                        ImageLoader.getInstance()?.load(it, object : Callback {
                            override fun onSuccess(url: String, bitmap: Bitmap) {
                                Log.d(MovieDetailsPresenter::class.java.name, "LOAD FROM DB")
                                iMovieDetailsView?.onDownloadFromBD(result, bitmap)
                            }

                            override fun onError(url: String, throwable: Throwable) {
                                iMovieDetailsView?.onDownloadDetailsError(throwable)
                                iMovieDetailsView?.hideLoading()
                            }
                        })
                    }
                }

                override fun onError(error: String) {
                    loadInformation(movieId)
                }
            })
    }

    private fun loadInformation(movieId: Int) {
        Injector.getMoviesRepositoryImpl()
            .getInformationAboutMovie(movieId, object :
                NetworkCallback<MovieDetails> {
                override fun onSuccess(result: MovieDetails) {

                    val imageUrl = result.image?.let { getImageUrl(it) }

                    imageUrl?.let {
                        ImageLoader.getInstance()?.load(it, object : Callback {
                            override fun onSuccess(url: String, bitmap: Bitmap) {
                                Log.d(MovieDetailsPresenter::class.java.name, "LOAD FROM NETWORK")
                                iMovieDetailsView?.onDownloadResultDetails(result, bitmap)
                                addInformationInBD(
                                    Information(
                                        result.id,
                                        result.title,
                                        result.description,
                                        result.releaseDate,
                                        convertTime(result.runtime),
                                        imageUrl
                                    )
                                )
                            }

                            override fun onError(url: String, throwable: Throwable) {
                                iMovieDetailsView?.onDownloadDetailsError(throwable)
                                iMovieDetailsView?.hideLoading()
                            }
                        })
                    }
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    private fun addInformationInBD(information: Information) {
        com.example.android.database.Injector.getDBRepositoryImpl().addInformation(information)
    }

    override fun onDownloadActorSquad(movieId: Int) {
        loadActorSquad(movieId)

//        com.example.android.database.Injector.getDBRepositoryImpl().loadActorSquad(
//            movieId,
//            object : com.example.android.database.Callback<List<ActorSquad>> {
//                override fun onSuccess(result: List<ActorSquad>) {
//                    iMovieDetailsView?.onDownloadActorSquadFromBd(result)
//                }
//
//                override fun onError(error: String) {
//                    loadActorSquad(movieId)
//                }
//            })
    }

    private fun loadActorSquad(movieId: Int) {
        Injector.getActorsRepositoryImpl()
            .getMovieActorsSquad(movieId, object : NetworkCallback<MovieActorSquad> {
                override fun onSuccess(result: MovieActorSquad) {
                    iMovieDetailsView?.onDownloadActorSquad(result)
                    //addActorSquadInBd(movieId, result.cast)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

//    private fun addActorSquadInBd(movieId: Int, cast: List<Cast>) {
//        val castList = mutableListOf<ActorSquad>()
//
//        for (i in 0 until cast.size) {
//            castList.add(
//                ActorSquad(movieId, cast[i].name, cast[i].character,
//                    cast[i].image?.let { getImageUrl(it) })
//            )
//        }
//
//        com.example.android.database.Injector.getDBRepositoryImpl().addActorSquad(movieId, castList)
//    }

    override fun onDownloadRecommendedMovies(movieId: Int) {
        loadRecommendedMovies(movieId)

//        com.example.android.database.Injector.getDBRepositoryImpl().loadRecommendedMovies(movieId,
//            object : com.example.android.database.Callback<List<Movie>> {
//                override fun onSuccess(result: List<Movie>) {
//                    iMovieDetailsView?.onDownloadRecommendedMoviesFromBd(result)
//                }
//
//                override fun onError(error: String) {
//                    loadRecommendedMovies(movieId)
//                }
//            })
    }

    private fun loadRecommendedMovies(movieId: Int) {
        Injector.getMoviesRepositoryImpl()
            .getRecommendedMoviesForMovie(movieId, object : NetworkCallback<MovieList> {
                override fun onSuccess(result: MovieList) {
                    iMovieDetailsView?.onDownloadRecommendedMovies(result)
                    //addRecommendedMovies(movieId, result.results)
                }

                override fun onError(throwable: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(throwable)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

//    private fun addRecommendedMovies(
//        movieId: Int,
//        result: List<com.example.android.network.models.movie.Movie>
//    ) {
//        val movieList = mutableListOf<Movie>()
//
//        for (i in 0 until result.size) {
//            movieList.add(
//                Movie(movieId, result[i].title, result[i].rating,
//                    result[i].image?.let { getImageUrl(it) })
//            )
//        }
//
//        com.example.android.database.Injector.getDBRepositoryImpl()
//            .addRecommendedMovies(movieId, movieList)
//    }

    override fun onDownloadVideo(movieId: Int) {
        com.example.android.database.Injector.getDBRepositoryImpl()
            .loadVideo(movieId, object : com.example.android.database.Callback<List<String>> {
                override fun onSuccess(result: List<String>) {
                    iMovieDetailsView?.onDownloadVideo(result)
                    iMovieDetailsView?.hideLoading()
                }

                override fun onError(error: String) {
                    loadVideo(movieId)
                }
            })
    }

    private fun loadVideo(movieId: Int) {
        Injector.getVideosRepositoryImpl()
            .getVideosForMovie(movieId, object : NetworkCallback<VideoList> {
                override fun onSuccess(result: VideoList) {
                    youTubeVideos.clear()

                    for (i in 0 until result.results.size) {
                        youTubeVideos.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${result.results[i].key}\" frameborder=\"0\" allowfullscreen></iframe>")
                    }

                    iMovieDetailsView?.onDownloadVideo(youTubeVideos)
                    addVideoInBD(movieId, youTubeVideos)
                    iMovieDetailsView?.hideLoading()
                }

                override fun onError(error: Throwable) {
                    iMovieDetailsView?.onDownloadDetailsError(error)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    private fun addVideoInBD(movieId: Int, videoList: List<String>) {
        com.example.android.database.Injector.getDBRepositoryImpl().addVideo(movieId, videoList)
    }

    override fun onDestroy() {
        iMovieDetailsView = null
    }

}