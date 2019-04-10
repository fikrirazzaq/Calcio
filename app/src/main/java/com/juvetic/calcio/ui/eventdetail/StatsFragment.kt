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


class StatsFragment : Fragment() {

    private lateinit var tvSpectators: TextView
    private lateinit var tvHomeShots: TextView
    private lateinit var tvAwayShots: TextView
    private lateinit var tvHomeYellowCard: TextView
    private lateinit var tvAwayYellowCard: TextView
    private lateinit var tvHomeRedCard: TextView
    private lateinit var tvAwayRedCard: TextView

    private lateinit var event: Event

    companion object {
        fun newInstance(event: Event): StatsFragment {
            val statsFragment = StatsFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_EVENT_ITEM, event)
            statsFragment.arguments = bundle
            return statsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_stats, container, false)

        tvHomeShots = v.findViewById(R.id.tv_home_shots)
        tvAwayShots = v.findViewById(R.id.tv_away_shots)
        tvHomeYellowCard = v.findViewById(R.id.tv_home_yellow_card)
        tvAwayYellowCard = v.findViewById(R.id.tv_away_yellow_card)
        tvHomeRedCard = v.findViewById(R.id.tv_home_red_card)
        tvAwayRedCard = v.findViewById(R.id.tv_away_red_card)
        tvSpectators = v.findViewById(R.id.tv_spectators)

        if (arguments != null) {
            event = arguments?.getParcelable(EXTRA_EVENT_ITEM)!!

            val e = event.events[0]
            tvHomeShots.text = e.intHomeShots
            tvAwayShots.text = e.intAwayShots
            tvHomeYellowCard.text = getCardsCount(e.strHomeYellowCards)
            tvAwayYellowCard.text = getCardsCount(e.strAwayYellowCards)
            tvHomeRedCard.text = getCardsCount(e.strHomeRedCards)
            tvAwayRedCard.text = getCardsCount(e.strAwayRedCards)
            tvSpectators.text = e.intSpectators ?: "-"
        }

        return v
    }

    private fun getCardsCount(s: String?): String {
        if (s == null) return "-"
        val count = s.length - s.replace(".", "").length
        return count.toString()
    }


}
