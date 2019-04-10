package com.juvetic.calcio.ui.eventdetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.juvetic.calcio.R
import com.juvetic.calcio.model.event.Event
import com.juvetic.calcio.ui.eventdetail.EventDetailFragment.Companion.EXTRA_EVENT_ITEM
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class LineUpFragment : Fragment(), AnkoLogger {

    private lateinit var tvHomeStarting: TextView
    private lateinit var tvAwayStarting: TextView
    private lateinit var tvHomeSubs: TextView
    private lateinit var tvAwaySubs: TextView
    private lateinit var tvHomeFormation: TextView
    private lateinit var tvAwayFormation: TextView

    companion object {

        fun newInstance(event: Event): LineUpFragment {
            val lineUpFragment = LineUpFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_EVENT_ITEM, event)
            lineUpFragment.arguments = bundle
            return lineUpFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_line_up, container, false)

        tvHomeStarting = v.findViewById(R.id.tv_home_starting)
        tvAwayStarting = v.findViewById(R.id.tv_away_starting)
        tvHomeSubs = v.findViewById(R.id.tv_home_substitute)
        tvAwaySubs = v.findViewById(R.id.tv_away_substitute)
        tvHomeFormation = v.findViewById(R.id.tv_home_formation)
        tvAwayFormation = v.findViewById(R.id.tv_away_formation)

        info("Arguments $arguments")
        if (arguments != null) {
            val event = arguments?.getParcelable<Event>(EXTRA_EVENT_ITEM)!!
            tvHomeStarting.text = getHomeStarting(event)
            tvAwayStarting.text = getAwayStarting(event)
            tvHomeSubs.text = getHomeSubs(event)
            tvAwaySubs.text = getAwaySubs(event)
            tvHomeFormation.text = event.events[0].strHomeFormation
            tvAwayFormation.text = event.events[0].strAwayFormation
        }

        return v
    }

    private fun getHomeStarting(event: Event): String {
        val e = event.events[0]
        val list: MutableList<String> = mutableListOf()
        e.strHomeLineupGoalkeeper?.let { list.addAll(it.trim().split(";")) }
        e.strHomeLineupDefense?.let { list.addAll(it.trim().split(";")) }
        e.strHomeLineupMidfield?.let { list.addAll(it.trim().split(";")) }
        e.strHomeLineupForward?.let { list.addAll(it.trim().split(";")) }

        var starting = ""
        for (s in list) {
            starting += (s.trim() + "\n")
        }

        info("home starting $starting")
        return starting
    }

    private fun getAwayStarting(event: Event): String {
        val e = event.events[0]
        val list: MutableList<String> = mutableListOf()
        e.strAwayLineupGoalkeeper?.let { list.addAll(it.trim().split(";")) }
        e.strAwayLineupDefense?.let { list.addAll(it.trim().split(";")) }
        e.strAwayLineupMidfield?.let { list.addAll(it.trim().split(";")) }
        e.strAwayLineupForward?.let { list.addAll(it.trim().split(";")) }

        var starting = ""
        for (s in list) {
            starting += (s.trim() + "\n")
        }

        info("away starting $starting")
        return starting
    }

    private fun getHomeSubs(event: Event): String {
        val e = event.events[0]
        val list: MutableList<String> = mutableListOf()
        e.strHomeLineupSubstitutes?.let { list.addAll(it.trim().split(";")) }

        var subs = ""
        for (s in list) {
            subs += (s.trim() + "\n")
        }

        info("home subs $subs")
        return subs
    }

    private fun getAwaySubs(event: Event): String {
        val e = event.events[0]
        val list: MutableList<String> = mutableListOf()
        e.strAwayLineupSubstitutes?.let { list.addAll(it.trim().split(";")) }

        var subs = ""
        for (s in list) {
            subs += (s.trim() + "\n")
        }

        info("away subs $subs")
        return subs
    }
}
