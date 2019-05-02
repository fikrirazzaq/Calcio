package com.juvetic.calcio.ui.search


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.searchevent.SearchEventInteractor
import com.juvetic.calcio.core.presenter.searchevent.SearchEventPresenter
import com.juvetic.calcio.model.event.EventSearch
import com.juvetic.calcio.ui.eventdetail.EventDetailActivity
import com.juvetic.calcio.ui.eventdetail.EventDetailFragment
import com.juvetic.calcio.utils.EventDetailClickListener
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.concurrent.schedule


class SearchFragment : Fragment(), LeagueContract<EventSearch>,
    EventDetailClickListener, AnkoLogger {

    private lateinit var toolBar: Toolbar
    private lateinit var rcvEvent: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var tvNotFound: TextView
    private lateinit var queryTextListener: SearchView.OnQueryTextListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_search, container, false)

        rcvEvent = v.findViewById(R.id.rcv_search_event)
        tvNotFound = v.findViewById(R.id.tv_notfound)
        toolBar = v.findViewById(R.id.toolbar)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolBar)
        toolBar.title = getString(R.string.search_matches)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        (activity as AppCompatActivity).menuInflater.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo((activity as AppCompatActivity).componentName))

        queryTextListener = object : SearchView.OnQueryTextListener {
            var timer = Timer()
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                timer.cancel()
                val sleep = when (newText?.length) {
                    1 -> 1000L
                    2, 3 -> 700L
                    4, 5 -> 500L
                    else -> 300L
                }
                timer = Timer()
                timer.schedule(sleep) {
                    if (!newText.isNullOrEmpty()) {
                        searchEvent(newText)
                    }
                }
                return true
            }

        }
        searchView.setOnQueryTextListener(queryTextListener)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> return false
        }

        searchView.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    override fun onGetDataSuccess(data: EventSearch?) {
        info("Success search event")
        showList()
        data?.let {
            val result = it.event

            val searchAdapter: SearchAdapter? = context?.let { it1 ->
                SearchAdapter(it1, result, this)
            }

            rcvEvent.adapter = searchAdapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcvEvent.layoutManager = linearLayoutManager
            rcvEvent.addItemDecoration(
                DividerItemDecoration(rcvEvent.context, linearLayoutManager.orientation)
            )
        }
    }

    override fun onDataError(message: String) {
        showNotFound()
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
        }
    }

    override fun onEventDetailClick(eventId: String?) {
        startActivity<EventDetailActivity>(EventDetailFragment.EVENT_ID to eventId)
    }

    private fun searchEvent(query: String?) {
        val presenter = SearchEventPresenter(this, SearchEventInteractor())
        query?.let { presenter.searchEvent(it) }
    }

    private fun showList() {
        rcvEvent.visibility = View.VISIBLE
        tvNotFound.visibility = View.GONE
    }

    private fun showNotFound() {
        rcvEvent.visibility = View.GONE
        tvNotFound.visibility = View.VISIBLE
    }
}
