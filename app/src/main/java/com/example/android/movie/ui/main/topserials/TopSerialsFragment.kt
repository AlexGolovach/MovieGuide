package com.example.android.movie.ui.main.topserials

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.movie.R
import com.example.android.movie.mvp.topserials.ITopSerialsPresenter
import com.example.android.movie.mvp.topserials.ITopSerialsView
import com.example.android.movie.search.IFragmentListener
import com.example.android.movie.search.ISearch
import com.example.android.movie.ui.base.BaseFragment
import com.example.android.movie.ui.main.details.SerialDetailsActivity
import com.example.android.movie.ui.utils.Constants.SERIAL_ID
import com.example.android.network.models.serial.Serial
import com.example.android.network.models.serial.SerialsList
import kotlinx.android.synthetic.main.fragment_top_serials.*

class TopSerialsFragment : BaseFragment(), ITopSerialsView,
    ISearch {

    private lateinit var topSerialsPresenter: ITopSerialsPresenter
    private lateinit var topAdapter: TopSerialsAdapter
    private lateinit var searchResultAdapter: SearchResultSerialsAdapter

    private var iFragmentListener: IFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iFragmentListener = context as IFragmentListener
        iFragmentListener!!.addiSearch(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_top_serials, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topAdapter = TopSerialsAdapter()
        topSerialsPresenter = TopSerialsPresenter(this)

        getData()
        initRecycler()
        initSearchResultRecycler()
    }

    private fun getData() {
        topSerialsPresenter.onDownloadSerials()
    }

    private fun initRecycler() {
        val context = recyclerView.context

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)

            val listener = object : TopSerialsAdapter.Listener {
                override fun onItemClicked(serial: Serial) {
                    val intent = Intent(activity, SerialDetailsActivity::class.java)

                    intent.putExtra(SERIAL_ID, serial.id)

                    startActivity(intent)
                }
            }

            val loadItems = object : TopSerialsAdapter.LoadItems {
                override fun onLoadItems(page: Int) {
                    if (isOnline()) {
                        topSerialsPresenter.onDownloadSerials(page)
                    }
                }

            }

            topAdapter.listener = listener
            topAdapter.loadListener = loadItems

            adapter = topAdapter
        }
    }

    private fun initSearchResultRecycler() {
        val context = searchResultRecyclerView.context

        searchResultAdapter = SearchResultSerialsAdapter()

        searchResultRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            val listener = object : SearchResultSerialsAdapter.OpenListener {
                override fun onItemClickedListener(serial: Serial) {
                    val intent = Intent(activity, SerialDetailsActivity::class.java)

                    intent.putExtra(SERIAL_ID, serial.id)

                    startActivity(intent)
                }
            }

            searchResultAdapter.openListener = listener
            adapter = searchResultAdapter
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onDownloadResult(serials: SerialsList) {
        topAdapter.setItems(serials)
    }

    override fun onTextQuery(text: String) {
        if (text.isEmpty()) {
            recyclerView.visibility = View.VISIBLE
            searchResultRecyclerView.visibility = View.GONE

            searchResultAdapter.updateItems(emptyList())
        } else {
            recyclerView.visibility = View.GONE
            searchResultRecyclerView.visibility = View.VISIBLE
            topSerialsPresenter.onSearchSerials(text)
        }
    }

    override fun onSearchResult(result: SerialsList) {
        searchResultAdapter.updateItems(result.results)
    }

    override fun onDownloadError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        iFragmentListener?.removeISearch(this)
    }

    override fun onDestroy() {
        topSerialsPresenter.onDestroy()

        super.onDestroy()
    }
}