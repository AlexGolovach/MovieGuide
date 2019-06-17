package com.example.android.movie.ui.main.details.movie

import android.util.Log
import com.example.android.database.DatabaseCallback
import com.example.android.database.DatabaseRepositoryManager
import com.example.android.database.IdGenerator
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video
import com.example.android.movie.App
import com.example.android.movie.mvp.moviedetails.IMovieDetailsPresenter
import com.example.android.movie.mvp.moviedetails.IMovieDetailsView
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.convertTime
import com.example.android.network.Converter.getImageUrl
import com.example.android.network.NetworkRepositoryManager
import com.example.android.network.NetworkCallback
import com.example.android.network.models.VideoList
import com.example.android.network.models.movie.MovieList
import com.example.android.network.models.moviedetails.MovieDetails
import com.example.android.network.models.moviesquad.MovieActorSquad

class MovieDetailsPresenter(
    private var isOnline: Boolean,
    private var iMovieDetailsView: IMovieDetailsView?
) :
    IMovieDetailsPresenter {

    private val castList = mutableListOf<ActorSquad>()
    private val movieList = mutableListOf<Movie>()
    private val youTubeVideos = mutableListOf<Video>()

    override fun onDownloadMovieDetails(movieId: Int) {
        iMovieDetailsView?.showLoading()

        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadInformation(
                    movieId,
                    object : DatabaseCallback<Details> {
                        override fun onSuccess(result: Details) {

                            iMovieDetailsView?.onDownloadResultDetails(result)
                        }

                        override fun onError(error: String) {
                            if (isOnline) {
                                loadInformation(movieId)
                            }
                        }
                    })
        }
    }

    private fun loadInformation(movieId: Int) {
        NetworkRepositoryManager.getMoviesRepositoryImpl()
            .getInformationAboutMovie(movieId, object :
                NetworkCallback<MovieDetails> {
                override fun onSuccess(result: MovieDetails) {
                    val imageUrl = result.image?.let { getImageUrl(it) }

                    val details = Details(
                        movieId, result.title, result.description, result.releaseDate,
                        convertTime(result.runtime), imageUrl
                    )
                    iMovieDetailsView?.onDownloadResultDetails(details)

                    addInformationInBD(details)
                }

                override fun onError(error: String) {
                    iMovieDetailsView?.onDownloadDetailsError(error)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    private fun addInformationInBD(details: Details) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .addInformation(details)
        }
    }

    override fun onDownloadActorSquad(movieId: Int) {
        App.get()?.let {
            com.example.android.database.DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadActorSquad(
                    movieId,
                    object : DatabaseCallback<List<ActorSquad>> {
                        override fun onSuccess(result: List<ActorSquad>) {
                            iMovieDetailsView?.onDownloadActorSquad(result)
                        }

                        override fun onError(error: String) {
                            if (isOnline) {
                                loadActorSquad(movieId)
                            }
                        }
                    })
        }
    }

    private fun loadActorSquad(movieId: Int) {
        NetworkRepositoryManager.getActorsRepositoryImpl()
            .getMovieActorsSquad(movieId, object : NetworkCallback<MovieActorSquad> {
                override fun onSuccess(result: MovieActorSquad) {

                    for (i in 0 until result.cast.size) {
                        castList.add(
                            ActorSquad(result.cast[i].id,
                                movieId,
                                result.cast[i].name,
                                result.cast[i].character,
                                result.cast[i].image?.let { getImageUrl(it) })
                        )
                    }
                    iMovieDetailsView?.onDownloadActorSquad(castList)
                    addActorSquadInBd(movieId, castList)
                }

                override fun onError(error: String) {
                    iMovieDetailsView?.onDownloadDetailsError(error)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    private fun addActorSquadInBd(movieId: Int, cast: List<ActorSquad>) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .addActorSquad(movieId, cast)
        }
    }

    override fun onDownloadRecommendedMovies(movieId: Int) {
        App.get()?.let {
            com.example.android.database.DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadRecommendedMovies(movieId,
                    object : DatabaseCallback<List<Movie>> {
                        override fun onSuccess(result: List<Movie>) {
                            iMovieDetailsView?.onDownloadRecommendedMovies(result)
                        }

                        override fun onError(error: String) {
                            if (isOnline) {
                                loadRecommendedMovies(movieId)
                            }
                        }
                    })
        }
    }

    private fun loadRecommendedMovies(movieId: Int) {
        NetworkRepositoryManager.getMoviesRepositoryImpl()
            .getRecommendedMoviesForMovie(movieId, object : NetworkCallback<MovieList> {
                override fun onSuccess(result: MovieList) {
                    for (i in 0 until result.results.size) {
                        movieList.add(
                            Movie(
                                result.results[i].id,
                                movieId,
                                result.results[i].title,
                                result.results[i].rating,
                                result.results[i].image?.let { getImageUrl(it) })
                        )
                    }

                    iMovieDetailsView?.onDownloadRecommendedMovies(movieList)
                    addRecommendedMovies(movieId, movieList)
                }

                override fun onError(error: String) {
                    iMovieDetailsView?.onDownloadDetailsError(error)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    private fun addRecommendedMovies(movieId: Int, movie: List<Movie>) {

        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .addRecommendedMovies(movieId, movie)
        }
    }

    override fun onDownloadVideo(movieId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadVideo(movieId, object : DatabaseCallback<List<Video>> {
                    override fun onSuccess(result: List<Video>) {
                        iMovieDetailsView?.onDownloadVideo(result)
                        iMovieDetailsView?.hideLoading()
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadVideo(movieId)
                        }
                    }
                })
        }
    }

    private fun loadVideo(movieId: Int) {
        NetworkRepositoryManager.getVideosRepositoryImpl()
            .getVideosForMovie(movieId, object : NetworkCallback<VideoList> {
                override fun onSuccess(result: VideoList) {
                    youTubeVideos.clear()

                    for (i in 0 until result.results.size) {
                        youTubeVideos.add(
                            Video(
                                IdGenerator.generateId(),
                                movieId,
                                "https://www.youtube.com/embed/${result.results[i].key}",
                                result.results[i].name
                            )
                        )
                    }

                    iMovieDetailsView?.onDownloadVideo(youTubeVideos)
                    addVideoInBD(movieId, youTubeVideos)

                    iMovieDetailsView?.hideLoading()
                }

                override fun onError(error: String) {
                    iMovieDetailsView?.onDownloadDetailsError(error)
                    iMovieDetailsView?.hideLoading()
                }
            })
    }

    private fun addVideoInBD(movieId: Int, videoList: MutableList<Video>) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .addVideo(movieId, videoList)
        }
    }

    override fun addToFavorite(movieId: Int, type: String) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadInformation(movieId, object : DatabaseCallback<Details> {
                    override fun onSuccess(result: Details) {
                        DatabaseRepositoryManager.getFavoriteDatabaseRepositoryImpl(it)
                            .addToFavorite(AccountOperation.getAccount().id, result, type)
                    }

                    override fun onError(error: String) {
                        Log.d(MovieDetailsPresenter::class.java.name, error)
                    }

                })
        }
    }

    override fun checkMovieInFavorites(userId: Int, movieId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .checkMovieInFavorite(userId, movieId, object : DatabaseCallback<Boolean> {
                    override fun onSuccess(result: Boolean) {
                        iMovieDetailsView?.isMovieInFavorites(result)
                    }

                    override fun onError(error: String) {
                        iMovieDetailsView?.onDownloadDetailsError(error)
                    }
                })
        }
    }

    override fun deleteFromFavorite(userId: Int, movieId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getFavoriteDatabaseRepositoryImpl(it)
                .deleteFromFavorites(userId, movieId)
        }
    }

    override fun onDestroy() {
        iMovieDetailsView = null
    }
}