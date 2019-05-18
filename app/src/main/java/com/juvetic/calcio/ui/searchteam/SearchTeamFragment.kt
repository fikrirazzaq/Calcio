package com.juvetic.calcio.ui.searchteam


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
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
import com.juvetic.calcio.core.presenter.searchteam.SearchTeamInteractor
import com.juvetic.calcio.core.presenter.searchteam.SearchTeamPresenter
import com.juvetic.calcio.model.team.Team
import com.juvetic.calcio.ui.teamdetail.TeamDetailActivity
import com.juvetic.calcio.ui.teamdetail.TeamDetailFragment
import com.juvetic.calcio.utils.TeamDetailClickListener
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.concurrent.schedule

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchTeamFragment : Fragment(), LeagueContract<Team>,
    TeamDetailClickListener, AnkoLogger {

    private lateinit var toolBar: Toolbar
    private lateinit var rcvTeam: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var tvNotFound: TextView
    private lateinit var llSearchInit: LinearLayout
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvTeam = view.findViewById(R.id.rcv_search_team)
        tvNotFound = view.findViewById(R.id.tv_notfound)
        toolBar = view.findViewById(R.id.toolbar)
        llSearchInit = view.findViewById(R.id.ll_search_init)

        (activity as AppCompatActivity).setSupportActionBar(toolBar)
        toolBar.title = "Search Teams"
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
                        searchTeam(newText)
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

    override fun onGetDataSuccess(data: Team?) {
        llSearchInit.visibility = View.GONE
        info("Success search event")
        showList()
        data?.let {
            val result = it.teams

            val searchAdapter: SearchTeamAdapter? = context?.let { it1 ->
                SearchTeamAdapter(it1, result, this)
            }

            rcvTeam.adapter = searchAdapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcvTeam.layoutManager = linearLayoutManager
            rcvTeam.addItemDecoration(
                DividerItemDecoration(rcvTeam.context, linearLayoutManager.orientation)
            )
        }
    }

    override fun onGetDataFailed(message: String) {
        showNotFound()
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
        }
    }

    override fun onTeamClick(teamId: String?) {
        startActivity<TeamDetailActivity>(TeamDetailFragment.TEAM_ID to teamId)
    }

    private fun searchTeam(query: String?) {
        val presenter = SearchTeamPresenter(this, SearchTeamInteractor())
        query?.let { presenter.searchTeam(it) }
    }

    private fun showList() {
        rcvTeam.visibility = View.VISIBLE
        tvNotFound.visibility = View.GONE
    }

    private fun showNotFound() {
        rcvTeam.visibility = View.GONE
        tvNotFound.visibility = View.VISIBLE
    }

}
