package com.example.android.movie.ui.fullserialsfilms.fullserials

import com.example.android.database.Callback
import com.example.android.database.Injector
import com.example.android.database.model.Serial
import com.example.android.movie.mvp.fullserials.IFullSerialsPresenter
import com.example.android.movie.mvp.fullserials.IFullSerialsView

class FullSerialsPresenter(private var iFullSerialsView: IFullSerialsView?): IFullSerialsPresenter{

    override fun onDownloadFullSerials() {
        iFullSerialsView?.showLoading()

        Injector.getFullSerialsFilmsRepositoryImpl().loadSerials(object : Callback<List<Serial>> {
            override fun onSuccess(result: List<Serial>) {
                iFullSerialsView?.onDownloadResult(result)
                iFullSerialsView?.hideLoading()
            }

            override fun onError(throwable: Throwable) {
                iFullSerialsView?.onDownloadError(throwable)
                iFullSerialsView?.hideLoading()
            }
        })
    }

    override fun onDestroy() {
        iFullSerialsView = null
    }

}