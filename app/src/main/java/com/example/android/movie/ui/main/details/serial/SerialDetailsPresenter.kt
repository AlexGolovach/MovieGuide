package com.example.android.movie.ui.main.details.serial

import android.util.Log
import com.example.android.database.DatabaseCallback
import com.example.android.database.DatabaseRepositoryManager
import com.example.android.database.IdGenerator
import com.example.android.database.model.ActorSquad
import com.example.android.database.model.Details
import com.example.android.database.model.Movie
import com.example.android.database.model.Video
import com.example.android.movie.App
import com.example.android.movie.mvp.serialdetails.ISerialDetailsPresenter
import com.example.android.movie.mvp.serialdetails.ISerialDetailsView
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.movie.ui.utils.convertTime
import com.example.android.network.Converter.getImageUrl
import com.example.android.network.NetworkCallback
import com.example.android.network.NetworkRepositoryManager
import com.example.android.network.models.SerialDetails
import com.example.android.network.models.VideoList
import com.example.android.network.models.recommendedserials.RecommendSerialsList
import com.example.android.network.models.serialsquad.SerialActorSquad

class SerialDetailsPresenter(
    private var isOnline: Boolean,
    private var iSerialDetailsView: ISerialDetailsView?
) :
    ISerialDetailsPresenter {

    private val castList = mutableListOf<ActorSquad>()
    private val movieList = mutableListOf<Movie>()
    private var youTubeVideos = mutableListOf<Video>()

    override fun onDownloadSerialDetails(serialId: Int) {
        iSerialDetailsView?.showLoading()

        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadInformation(serialId, object : DatabaseCallback<Details> {
                    override fun onSuccess(result: Details) {

                        iSerialDetailsView?.onDownloadResultDetails(result)
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadSerialDetails(serialId)
                        }
                    }
                })
        }
    }

    private fun loadSerialDetails(serialId: Int) {
        NetworkRepositoryManager.getSerialsRepositoryImpl()
            .getSerialDetails(serialId, object : NetworkCallback<SerialDetails> {
                override fun onSuccess(result: SerialDetails) {
                    val imageUrl = result.image?.let { getImageUrl(it) }

                    val details = Details(
                        serialId, result.name, result.overview, result.releaseDate,
                        convertTime(result.episodeTime[0]), imageUrl
                    )

                    iSerialDetailsView?.onDownloadResultDetails(details)

                    addDetailsInBD(details)
                }

                override fun onError(error: String) {
                    iSerialDetailsView?.onDownloadDetailsError(error)
                    iSerialDetailsView?.hideLoading()
                }

            })
    }

    private fun addDetailsInBD(details: Details) {

        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it).addInformation(details)
        }
    }

    override fun onDownloadActorSquad(serialId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadActorSquad(serialId, object : DatabaseCallback<List<ActorSquad>> {
                    override fun onSuccess(result: List<ActorSquad>) {

                        iSerialDetailsView?.onDownloadActorSquad(result)
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadActorSquad(serialId)
                        }
                    }
                })
        }
    }

    private fun loadActorSquad(serialId: Int) {
        NetworkRepositoryManager.getActorsRepositoryImpl().getSerialActorSquad(serialId, object :
            NetworkCallback<SerialActorSquad> {
            override fun onSuccess(result: SerialActorSquad) {
                for (i in 0 until result.cast.size) {
                    castList.add(
                        ActorSquad(result.cast[i].id,
                            serialId,
                            result.cast[i].name,
                            result.cast[i].character,
                            result.cast[i].profileImage?.let { getImageUrl(it) })
                    )
                }

                iSerialDetailsView?.onDownloadActorSquad(castList)
                addActorSquadInBD(serialId, castList)
            }

            override fun onError(error: String) {
                iSerialDetailsView?.onDownloadDetailsError(error)
                iSerialDetailsView?.hideLoading()
            }
        })
    }

    private fun addActorSquadInBD(serialId: Int, castList: List<ActorSquad>) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .addActorSquad(serialId, castList)
        }
    }

    override fun onDownloadRecommendedSerials(serialId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadRecommendedMovies(serialId, object : DatabaseCallback<List<Movie>> {
                    override fun onSuccess(result: List<Movie>) {

                        iSerialDetailsView?.onDownloadRecommendedSerials(result)
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadRecommendedSerials(serialId)
                        }
                    }
                })
        }
    }

    private fun loadRecommendedSerials(serialId: Int) {
        NetworkRepositoryManager.getSerialsRepositoryImpl()
            .getRecommendedSerialsForSerial(
                serialId,
                object : NetworkCallback<RecommendSerialsList> {
                    override fun onSuccess(result: RecommendSerialsList) {
                        for (i in 0 until result.results.size) {
                            result.results[i].id?.let {
                                Movie(
                                    it,
                                    serialId,
                                    result.results[i].title,
                                    result.results[i].rating,
                                    result.results[i].image?.let { image ->
                                        getImageUrl(image)
                                    })
                            }?.let {
                                movieList.add(
                                    it
                                )
                            }
                        }

                        iSerialDetailsView?.onDownloadRecommendedSerials(movieList)
                        addRecommendedSerialsInBD(serialId, movieList)
                    }

                    override fun onError(error: String) {
                        iSerialDetailsView?.onDownloadDetailsError(error)
                        iSerialDetailsView?.hideLoading()
                    }
                })
    }

    private fun addRecommendedSerialsInBD(serialId: Int, movieList: MutableList<Movie>) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .addRecommendedMovies(serialId, movieList)
        }
    }

    override fun onDownloadVideo(serialId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadVideo(serialId, object : DatabaseCallback<List<Video>> {
                    override fun onSuccess(result: List<Video>) {
                        iSerialDetailsView?.onDownloadVideo(result)
                        iSerialDetailsView?.hideLoading()
                    }

                    override fun onError(error: String) {
                        if (isOnline) {
                            loadVideo(serialId)
                        }
                    }
                })
        }
    }

    private fun loadVideo(serialId: Int) {
        NetworkRepositoryManager.getVideosRepositoryImpl()
            .getVideosForSerial(serialId, object : NetworkCallback<VideoList> {
                override fun onSuccess(result: VideoList) {
                    youTubeVideos.clear()


                    for (i in 0 until result.results.size) {
                        youTubeVideos.add(
                            Video(
                                IdGenerator.generateId(),
                                serialId,
                                "https://www.youtube.com/embed/${result.results[i].key}",
                                result.results[i].name
                            )
                        )
                    }

                    iSerialDetailsView?.onDownloadVideo(youTubeVideos)
                    addVideoInBD(serialId, youTubeVideos)

                    iSerialDetailsView?.hideLoading()
                }

                override fun onError(error: String) {
                    iSerialDetailsView?.onDownloadDetailsError(error)
                    iSerialDetailsView?.hideLoading()
                }
            })
    }

    private fun addVideoInBD(movieId: Int, videoList: List<Video>) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .addVideo(movieId, videoList)
        }
    }

    override fun addToFavorite(serialId: Int, type: String) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .loadInformation(serialId, object : DatabaseCallback<Details> {
                    override fun onSuccess(result: Details) {
                        DatabaseRepositoryManager.getFavoriteDatabaseRepositoryImpl(it)
                            .addToFavorite(AccountOperation.getAccount().id, result, type)
                    }

                    override fun onError(error: String) {
                        Log.d(SerialDetailsPresenter::class.java.name, error)
                    }

                })
        }
    }

    override fun checkSerialInFavorites(userId: Int, serialId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getMovieDatabaseRepositoryImpl(it)
                .checkMovieInFavorite(userId, serialId, object : DatabaseCallback<Boolean> {
                    override fun onSuccess(result: Boolean) {
                        iSerialDetailsView?.isSerialInFavorites(result)
                    }

                    override fun onError(error: String) {
                        Log.d(SerialDetailsPresenter::class.java.name, error)
                    }
                })
        }
    }

    override fun deleteFromFavorite(userId: Int, serialId: Int) {
        App.get()?.let {
            DatabaseRepositoryManager.getFavoriteDatabaseRepositoryImpl(it)
                .deleteFromFavorites(userId, serialId)
        }
    }

    override fun onDestroy() {
        iSerialDetailsView = null
    }

}