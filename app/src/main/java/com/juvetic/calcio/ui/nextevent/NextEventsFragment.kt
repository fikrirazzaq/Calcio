package com.juvetic.calcio.ui.nextevent


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private lateinit var rcvNextEvent: RecyclerView

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
        return inflater.inflate(R.layout.fragment_next_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvNextEvent = view.findViewById(R.id.rcv_next_event)

        if (arguments != null) {
            leagueId = arguments?.getString(LeagueDetailFragment.LEAGUE_ID)!!
            presenter = NextEventPresenter(this, NextEventInteractor())
            presenter.getNextEvent(leagueId)
        }
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
            rcvNextEvent.adapter = nextEventAdapter
            val linearLayoutManager = LinearLayoutManager(activity)
            rcvNextEvent.layoutManager = linearLayoutManager
            rcvNextEvent.addItemDecoration(
                DividerItemDecoration(
                    rcvNextEvent.context,
                    linearLayoutManager.orientation
                )
            )
        }
    }

    override fun onGetDataFailed(message: String) {
        activity?.runOnUiThread {
            debug("Error $message")
            toast("Request timeout, please try again")
            activity?.onBackPressed()
        }
    }
}
