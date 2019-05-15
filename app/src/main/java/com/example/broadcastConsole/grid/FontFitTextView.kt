package com.example.broadcastConsole.grid

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ScrollView
import android.widget.TextView
import com.example.broadcastConsole.pxToSp
import kotlin.concurrent.thread

class FontFitTextView : TextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun extractParentNonScrollableView() = extractParentNonScrollableView(parent)
    private fun extractParentNonScrollableView(Parent: ViewParent?): ViewParent? = when (Parent) {
            is ScrollView -> extractParentNonScrollableView(Parent.parent)
            else -> Parent
        }

    var columns = 8
    var ready = false
    private var currentLine: CharSequence? = null
    private lateinit var mUpdateThread : Thread
    var mStarted : Boolean = false
    var mainThread: Activity? = null
    var WIDTH  = 1440
    var HEIGHT = 2960
    private var WIDTHOLD = 1440
    private var HEIGHTOLD = 2960

    override fun onDraw(canvas: Canvas?) {
        ready = true
//        if (canvas != null) {
//            val canvas = canvas!!
//            canvas.drawText()
//        }
        super.onDraw(canvas)
    }

    fun SWAP() {
        HEIGHTOLD = HEIGHT
        WIDTHOLD = WIDTH
        HEIGHT = WIDTHOLD
        WIDTH = HEIGHTOLD
    }

    fun DRAW(): Boolean {
        val p = extractParentNonScrollableView()
        Log.e("ONTEXTCHANGE", "p is $p")
        if (p != null) {
            val p = p as ViewGroup
            Log.e("ONTEXTCHANGE", "WIDTH is $WIDTH")
            Log.e("ONTEXTCHANGE", "HEIGHT is $HEIGHT")
            val paint = d.adjustTextSize(
                getPaint(),
                " ".repeat(columns),
                columns,
                WIDTH,
                HEIGHT
            )
            return if (paint == null) {
                Log.e("ONTEXTCHANGE", "SET SIZE TO null")
                false
            } else {
                paint.textSize = paint.textSize.pxToSp(this.context)
                textSize = paint.textSize
                true
            }
        }
        return false
    }

    fun DRAWTHREAD() {
        // could use onPreDraw to detect if parent is measurable

        if (!mStarted) {
            mStarted = true
            mUpdateThread = thread {
                while(true) {
                    if (ready) {
                        ready = false
                        Log.e("ONTEXTCHANGE", "READY, RUNNING DRAW ON UI THREAD")
                        mainThread!!.runOnUiThread {
                            DRAW()
                        }
                        break
                    }
                    Log.e("ONTEXTCHANGE", "NOT READY")
                    Thread.sleep(16)
                }
            }
        }
        else {
            Log.e("ONTEXTCHANGE", "READY, RUNNING DRAW ON MAIN THREAD")
            DRAW()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        WIDTH = w
        HEIGHT = h
        ready = true
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        DRAWTHREAD()
        // TODO: reformat text due to wrapping and text size in order to wrap correctly
    }
}