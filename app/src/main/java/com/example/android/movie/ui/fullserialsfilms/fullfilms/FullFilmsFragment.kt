package com.example.android.movie.ui.fullserialsfilms.fullfilms

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
import com.example.android.database.model.Film
import com.example.android.movie.R
import com.example.android.movie.mvp.fullfilms.IFullFilmsPresenter
import com.example.android.movie.mvp.fullfilms.IFullFilmsView
import com.example.android.movie.ui.base.search.IFragmentListener
import com.example.android.movie.ui.base.search.ISearch
import com.example.android.movie.ui.player.PlayerActivity
import kotlinx.android.synthetic.main.fragment_full_films.*
import java.lang.NullPointerException

class FullFilmsFragment : Fragment(), IFullFilmsView, ISearch {

    private lateinit var fullFilmsPresenter: IFullFilmsPresenter
    private lateinit var filmsAdapter: FullFilmsAdapter

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
    ): View? = inflater.inflate(R.layout.fragment_full_films, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fullFilmsPresenter = FullFilmsPresenter(this)
        filmsAdapter = FullFilmsAdapter()

        getData()
        initRecycler()
    }

    private fun getData() {
        fullFilmsPresenter.onDownloadFullFilms()
    }

    private fun initRecycler() {
        val context = recycler_view.context

        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))
            setHasFixedSize(true)

            val listener = object : FullFilmsAdapter.OpenListener {
                override fun onItemClickedListener(movie: Film) {
                    val intent = Intent(activity, PlayerActivity::class.java)

                    intent.putExtra("VIDEO_ID", movie.videoId)

                    startActivity(intent)
                }
            }

            filmsAdapter.openListener = listener
            adapter = filmsAdapter
        }
    }

    override fun onDownloadResult(result: List<Film>) {
        filmsAdapter.setItems(result)
    }

    override fun onTextQuery(text: String) {
        filmsAdapter.filter.filter(text)
    }

    override fun onDownloadError(throwable: Throwable) {
        if (throwable is NullPointerException) {
            Toast.makeText(activity, "No films", Toast.LENGTH_SHORT).show()
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

    override fun onDetach() {
        super.onDetach()
        mIFragmentListener?.removeISearch(this)
    }

    override fun onDestroy() {
        fullFilmsPresenter.onDestroy()

        super.onDestroy()
    }
}