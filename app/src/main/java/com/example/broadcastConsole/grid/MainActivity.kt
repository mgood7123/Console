package com.example.broadcastConsole.grid

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.util.Log
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.example.broadcastConsole.R
import kotlinx.android.synthetic.main.table_empty.*

class MainActivity : AppCompatActivity() {

    var textView: FontFitTextView? = null
    var built = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun t(activity: Activity) = FontFitTextView(this).also {
            it.mainThread = activity
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.setTypeface(Typeface.MONOSPACE, Typeface.BOLD)
            it.hyphenationFrequency = Layout.HYPHENATION_FREQUENCY_NONE
            it.breakStrategy = Layout.BREAK_STRATEGY_SIMPLE
            it.columns = 3
        }

        setContentView(R.layout.table_empty)

        fun build(viewGroup: ViewGroup) {
            if (built) return
            textView = t(this)
            val textView = textView!!
            textView.columns = 3
            textView.text = "HELP ME PLEASE"
            val scroll = Zoomable(this)
            scroll.addView(textView)
            viewGroup.addView(scroll, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ))
            viewGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        Log.e("ONTEXTCHANGE", "Layout ready")
                        Log.e("ONTEXTCHANGE", "onStart WIDTH is ${viewGroup.width}")
                        Log.e("ONTEXTCHANGE", "onStart HEIGHT is ${viewGroup.height}")
                        textView.WIDTH = viewGroup.width
                        textView.HEIGHT = viewGroup.height
                        textView.ready = true
                    }
                }
            )
            built = true
            return
        }
        build(ConstraintOnly)
    }

    override fun onStart() {
        super.onStart()
        Log.e("ONTEXTCHANGE", "onStart WIDTH is ${ConstraintOnly.width}")
        Log.e("ONTEXTCHANGE", "onStart HEIGHT is ${ConstraintOnly.height}")
        textView!!.ready = true
    }

    override fun onResume() {
        super.onResume()
        Log.e("ONTEXTCHANGE", "onResume WIDTH is ${ConstraintOnly.width}")
        Log.e("ONTEXTCHANGE", "onResume HEIGHT is ${ConstraintOnly.height}")
        textView!!.ready = true
    }

    override fun onPause() {
        super.onPause()
        Log.e("ONTEXTCHANGE", "onPause WIDTH is ${ConstraintOnly.width}")
        Log.e("ONTEXTCHANGE", "onPause HEIGHT is ${ConstraintOnly.height}")
    }

    override fun onStop() {
        super.onStop()
        Log.e("ONTEXTCHANGE", "onStop WIDTH is ${ConstraintOnly.width}")
        Log.e("ONTEXTCHANGE", "onStop HEIGHT is ${ConstraintOnly.height}")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("ONTEXTCHANGE", "onConfigurationChanged ORIENTATION_LANDSCAPE WIDTH is ${ConstraintOnly.width}")
            Log.e("ONTEXTCHANGE", "onConfigurationChanged ORIENTATION_LANDSCAPE HEIGHT is ${ConstraintOnly.height}")
            textView!!.mStarted = false
            textView!!.ready = false
            textView!!.SWAP()
            textView!!.DRAWTHREAD()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("ONTEXTCHANGE", "onConfigurationChanged ORIENTATION_PORTRAIT WIDTH is ${ConstraintOnly.width}")
            Log.e("ONTEXTCHANGE", "onConfigurationChanged ORIENTATION_PORTRAIT HEIGHT is ${ConstraintOnly.height}")
            textView!!.mStarted = false
            textView!!.ready = false
            textView!!.SWAP()
            textView!!.DRAWTHREAD()
        }
        textView!!.ready = true
    }
}
