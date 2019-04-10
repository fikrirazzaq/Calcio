package com.juvetic.calcio.ui.search


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juvetic.calcio.R
import org.jetbrains.anko.AnkoLogger


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment(), AnkoLogger {

    companion object {
        fun newInstance(context: Context): SearchFragment {
            return SearchFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_news, container, false)

        return v
    }
}
