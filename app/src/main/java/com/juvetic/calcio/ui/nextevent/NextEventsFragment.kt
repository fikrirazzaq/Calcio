package com.juvetic.calcio.ui.nextevent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.juvetic.calcio.R
import com.juvetic.calcio.core.contract.LeagueContract
import com.juvetic.calcio.core.presenter.nextevent.NextEventInteractor
import com.juvetic.calcio.core.presenter.nextevent.NextEventPresenter
import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.model.event.EventResult
import com.juvetic.calcio.ui.eventdetail.EventDetailActivity
import com.juvetic.calcio.ui.eventdetail.EventDetailFragment.Companion.EVENT_ID
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailFragment
import com.juvetic.calcio.utils.EventDetailClickListener
import kotlinx.android.synthetic.main.fragment_next_events.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 *
 */
class NextEventsFragment : Fragment(), LeagueContract<Event>, AnkoLogger, EventDetailClickListener {

    private lateinit var leagueId: String
    private lateinit var presenter: NextEventPresenter

    companion object {
        fun newInstance(id: String): NextEventsFragment {
            val nextEventsFragment = NextEventsFragment()
            val bundle = Bundle()
            bundle.putString(LeagueDetailFragment.LEAGUE_ID, id)
            nextEventsFragment.arguments = bundle
            return nextEventsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_next_events, container, false)

        if (arguments != null) {
            leagueId = arguments?.getString(LeagueDetailFragment.LEAGUE_ID)!!
            presenter = NextEventPresenter(this, NextEventInteractor())
            presenter.getNextEvent(leagueId)
        }

        return v
    }

    override fun onEventDetailClick(eventId: String?) {
        info("next event $eventId")
        startActivity<EventDetailActivity>(EVENT_ID to eventId)
    }

    override fun onGetDataSuccess(data: Event?) {
        info("Success next event")
        data?.let {
            val result: List<EventResult> = it.events

            val nextEventAdapter: NextEventAdapter? = context?.let { it1 ->
                NextEventAdapter(it1, result, this)
            }
            rcv_next_event.adapter = nextEventAdapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcv_next_event.layoutManager = linearLayoutManager
            rcv_next_event.addItemDecoration(DividerItemDecoration(rcv_next_event.context, linearLayoutManager.orientation))
        }
    }

    override fun onDataError(message: String) {
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
            activity?.onBackPressed()
        }
    }
}
