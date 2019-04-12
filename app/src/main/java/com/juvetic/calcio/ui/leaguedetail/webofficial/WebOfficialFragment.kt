package com.juvetic.calcio.ui.leaguedetail.webofficial


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.juvetic.calcio.R
import com.juvetic.calcio.ui.leaguedetail.LeagueDetailFragment.Companion.WEB_URL
import com.juvetic.calcio.utils.MyWebChromeClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * A simple [Fragment] subclass.
 *
 */
class WebOfficialFragment : Fragment(), AnkoLogger {

    private var downX: Float = 0.0f
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    companion object {
        fun newInstance(url: String): WebOfficialFragment {
            val webOfficialFragment = WebOfficialFragment()
            val bundle = Bundle()
            bundle.putString(WEB_URL, url)
            webOfficialFragment.arguments = bundle
            return webOfficialFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_web_official, container, false)

        progressBar = v.findViewById(R.id.progress_bar)
        webView = v.findViewById(R.id.webview)

        info(arguments?.get(WEB_URL) as String?)
        if (arguments != null) {
            webView.loadUrl("https://" + arguments?.get(WEB_URL) as String?)
        }
        initWebView()

        return v
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    private fun initWebView() {
        activity?.let { MyWebChromeClient(it) }
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }
        }
        webView.clearCache(true)
        webView.clearHistory()
        webView.settings.javaScriptEnabled = true
        webView.isHorizontalScrollBarEnabled = false
        webView.setOnTouchListener(View.OnTouchListener { _, event ->
            if (event?.pointerCount!! > 1) {
                //Multi touch detected
                return@OnTouchListener true
            }

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // save the x
                    downX = event.x
                }

                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    // set x so that it doesn't move
                    event.setLocation(downX, event.y)
                }
            }
            return@OnTouchListener false
        })
    }

}
