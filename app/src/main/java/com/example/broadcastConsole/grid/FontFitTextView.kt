package com.example.broadcastConsole.grid

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.example.broadcastConsole.pxToSp
import kotlin.concurrent.thread

class FontFitTextView : TextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    var columns = 8
    var ready = false
    private var currentLine: CharSequence? = null
    private lateinit var mUpdateThread : Thread
    var mStarted : Boolean = false
    var mainThread: Activity? = null
    var WIDTH  = 1440
    var HEIGHT = 2960

    fun DRAW(): Boolean {
        var returnValue: Boolean = false
        mainThread!!.runOnUiThread {
            val paint = d.adjustTextSize(
                getPaint(),
                " ".repeat(columns),
                columns,
                WIDTH,
                HEIGHT
            )
            if (paint == null) Log.e("ONTEXTCHANGE", "SET SIZE TO null")
            else {
                paint.textSize = paint.textSize.pxToSp(this.context)
                textSize = paint.textSize
                textScaleX = 1f
//                textScaleY = 2f
                returnValue = true
            }
        }
        return returnValue
    }

    fun DRAWTHREAD() {
        if (!mStarted) {
            mStarted = true
            mUpdateThread = thread {
                while(true) {
                    if (ready) {
                        ready = false
                        DRAW()
                        break
                    }
                    Log.e("ONTEXTCHANGE", "NOT READY")
                    Thread.sleep(16)
                }
            }
        }
        else {
            DRAW()
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
//        DRAWTHREAD()
//        TODO: reformat text due to wrapping and text size in order to wrap correctly
    }
}