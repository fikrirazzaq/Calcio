package com.juvetic.calcio.ui.lastevent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.pastevent.PastEventInteractor
import com.juvetic.calcio.core.presenter.pastevent.PastEventPresenter
import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.model.event.EventResult
import com.juvetic.calcio.ui.eventdetail.EventDetailActivity
import com.juvetic.calcio.ui.eventdetail.EventDetailFragment
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailFragment
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailFragment.Companion.LEAGUE_ID
import com.juvetic.calcio.utils.EventDetailClickListener
import kotlinx.android.synthetic.main.fragment_last_events.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class LastEventsFragment : Fragment(), LeagueContract<Event>, AnkoLogger, EventDetailClickListener {

    private lateinit var leagueId: String
    private lateinit var presenter: PastEventPresenter

    companion object {
        fun newInstance(id: String): LastEventsFragment {
            val lastEventFragment = LastEventsFragment()
            val bundle = Bundle()
            bundle.putString(LEAGUE_ID, id)
            lastEventFragment.arguments = bundle
            return lastEventFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_last_events, container, false)

        if (arguments != null) {
            leagueId = arguments?.getString(LeagueDetailFragment.LEAGUE_ID)!!
            presenter = PastEventPresenter(this, PastEventInteractor())
            presenter.getEventDetail(leagueId)
        }

        return v
    }

    override fun onGetDataSuccess(data: Event?) {
        info("Success past event")
        data?.let {
            val result: List<EventResult> = it.events

            val lastEventAdapter: LastEventAdapter? = context?.let { it1 ->
                LastEventAdapter(it1, result, this)
            }

            rcv_last_event.adapter = lastEventAdapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcv_last_event.layoutManager = linearLayoutManager
            rcv_last_event.addItemDecoration(DividerItemDecoration(rcv_last_event.context, linearLayoutManager.orientation))
        }
    }

    override fun onDataError(message: String) {
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
            activity?.onBackPressed()
        }
    }

    override fun onEventDetailClick(eventId: String?) {
        startActivity<EventDetailActivity>(EventDetailFragment.EVENT_ID to eventId)
    }
}
