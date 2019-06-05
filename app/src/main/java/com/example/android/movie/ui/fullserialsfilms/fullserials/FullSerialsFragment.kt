package com.example.android.movie.ui.fullserialsfilms.fullserials

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.database.model.Serial
import com.example.android.movie.R
import com.example.android.movie.mvp.fullserials.IFullSerialsPresenter
import com.example.android.movie.mvp.fullserials.IFullSerialsView
import com.example.android.movie.ui.base.search.IFragmentListener
import com.example.android.movie.ui.base.search.ISearch
import com.example.android.movie.ui.player.PlayerActivity
import kotlinx.android.synthetic.main.fragment_full_serials.*

class FullSerialsFragment: Fragment(), IFullSerialsView, ISearch {

    private lateinit var fullSerialsPresenter: IFullSerialsPresenter
    private lateinit var serialsAdapter: FullSerialsAdapter

    private var mIFragmentListener: IFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mIFragmentListener = context as IFragmentListener
        mIFragmentListener!!.addiSearch(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_full_serials,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullSerialsPresenter = FullSerialsPresenter(this)
        serialsAdapter = FullSerialsAdapter()

        getData()
        initRecycler()
    }

    private fun getData() {
        fullSerialsPresenter.onDownloadFullSerials()
    }

    private fun initRecycler() {
        val context = recycler_view.context

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
            setHasFixedSize(true)

            val listener = object : FullSerialsAdapter.OpenListener {
                override fun onItemClickedListener(serial: Serial) {
                    val intent = Intent(activity, PlayerActivity::class.java)

                    intent.putExtra("VIDEO_ID", serial.videoId)

                    startActivity(intent)
                }
            }

            serialsAdapter.openListener = listener
            adapter = serialsAdapter
        }
    }

    override fun onDownloadResult(result: List<Serial>) {
        serialsAdapter.setItems(result)
    }

    override fun onTextQuery(text: String) {
        serialsAdapter.filter.filter(text)
    }

    override fun onDownloadError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, "No serials", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        if (progress_bar.visibility == View.VISIBLE) {
            progress_bar.visibility = View.GONE
            recycler_view.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        fullSerialsPresenter.onDestroy()

        super.onDestroy()
    }
}