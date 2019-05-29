package com.example.android.movie.ui.main.information.details.show

import com.example.android.database.Callback
import com.example.android.database.model.Reviews
import com.example.android.movie.mvp.showdetails.IShowDetailsPresenter
import com.example.android.movie.mvp.showdetails.IShowDetailsView
import com.example.android.movie.ui.utils.AccountOperation
import com.example.android.network.Injector
import com.example.android.network.models.video.VideosList
import com.example.android.network.models.recommendedshows.RecommendShowsList
import com.example.android.network.models.showdetails.ShowDetails
import com.example.android.network.models.showsquad.ActorShowSquad
import com.example.android.network.repository.actors.ActorsCallback
import com.example.android.network.repository.shows.ShowsCallback
import com.example.android.network.repository.videos.VideosCallback

class ShowDetailsPresenter(private var iShowDetailsView: IShowDetailsView?) :
    IShowDetailsPresenter {

    private var youTubeVideos = mutableListOf<String>()

    override fun onDownloadShowDetails(showId: Int) {
        iShowDetailsView?.showLoading()

        Injector.getShowsRepositoryImpl()
            .getShowDetails(showId, object : ShowsCallback<ShowDetails> {
                override fun onSuccess(result: ShowDetails) {
                    iShowDetailsView?.onDownloadResultDetails(result)
                }

                override fun onError(throwable: Throwable) {
                    iShowDetailsView?.onDownloadDetailsError(throwable)
                    iShowDetailsView?.hideLoading()
                }

            })
    }

    override fun onDownloadActorSquad(showId: Int) {
        Injector.getActorsRepositoryImpl().getShowActorSquad(showId, object :
            ActorsCallback<ActorShowSquad> {
            override fun onSuccess(result: ActorShowSquad) {
                iShowDetailsView?.onDownloadActorSquad(result)
            }

            override fun onError(throwable: Throwable) {
                iShowDetailsView?.onDownloadDetailsError(throwable)
                iShowDetailsView?.hideLoading()
            }
        })
    }

    override fun onDownloadRecommendedShows(showId: Int) {
        Injector.getShowsRepositoryImpl()
            .getRecommendedShowsForShow(showId, object : ShowsCallback<RecommendShowsList> {
                override fun onSuccess(result: RecommendShowsList) {
                    iShowDetailsView?.onDownloadRecommendedShows(result)
                }

                override fun onError(throwable: Throwable) {
                    iShowDetailsView?.onDownloadDetailsError(throwable)
                    iShowDetailsView?.hideLoading()
                }
            })
    }

    override fun onDownloadVideo(showId: Int) {
        Injector.getVideosRepositoryImpl().getVideosForShow(showId, object : VideosCallback {
            override fun onSuccess(result: VideosList) {
                youTubeVideos.clear()

                for (i in 0 until result.results.size) {
                    youTubeVideos.add("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/${result.results[i].key}\" frameborder=\"0\" allowfullscreen></iframe>")
                }

                iShowDetailsView?.onDownloadVideo(youTubeVideos)
                iShowDetailsView?.hideLoading()
            }

            override fun onError(error: Throwable) {
                iShowDetailsView?.hideLoading()
            }
        })
    }

    override fun addShowToFavorites(showId: Int) {
        Injector.getShowsRepositoryImpl().getShowDetails(showId, object :ShowsCallback<ShowDetails> {
                override fun onSuccess(result: ShowDetails) {
                    com.example.android.database.Injector.getFavoriteShowsRepositoryImpl()
                        .addShowInFavorites(AccountOperation.getAccount().id, result)
                }

                override fun onError(throwable: Throwable) {
                    iShowDetailsView?.onDownloadDetailsError(throwable)
                }
            })
    }

    override fun checkShowInFavorites(showId: Int) {
        com.example.android.database.Injector.getFavoriteShowsRepositoryImpl()
            .checkShowInFavorites(showId, object :
                Callback<Boolean> {
                override fun onSuccess(result: Boolean) {
                    iShowDetailsView?.isMovieAddedInFavorites(result)
                }

                override fun onError(throwable: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }

    override fun addReviewForShow(showId: Int, userName: String, review: String) {
        com.example.android.database.Injector.getReviewsRepositoryImpl()
            .addReviewForFilm(showId, userName, review)
    }

    override fun loadReviewForShow(showId: Int) {
        com.example.android.database.Injector.getReviewsRepositoryImpl()
            .loadReviewsForFilm(showId, object : Callback<ArrayList<Reviews>> {
                override fun onSuccess(result: ArrayList<Reviews>) {
                    iShowDetailsView?.onDownloadReviewsForShow(result)
                }

                override fun onError(throwable: Throwable) {
                    iShowDetailsView?.onDownloadDetailsError(throwable)
                }
            })
    }

    override fun deleteReviewForShow(documentId: String) {
        com.example.android.database.Injector.getReviewsRepositoryImpl().deleteReview(documentId)
    }

    override fun onDestroy() {
        iShowDetailsView = null
    }

}