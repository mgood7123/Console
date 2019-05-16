package com.example.broadcastConsole.grid

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.view.ViewGroup
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
            textView.columns = 1
            textView.text = "H"
            val scroll = Zoomable(this)
            scroll.addView(textView)
            viewGroup.addView(scroll, ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ))
            built = true
            return
        }
        build(ConstraintOnly)
    }
}
