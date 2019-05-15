package com.example.broadcastConsole.grid

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ScrollView

class Zoomable : ScrollView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val child = getChildAt(0) as FontFitTextView
        child.HEIGHT = h
        child.width = w
        child.ready = true
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        // get text view
        val child = getChildAt(0) as FontFitTextView
        child.HEIGHT = height
        child.width = width
        child.ready = true
        child.DRAW()
        super.onDraw(canvas)
    }

    private var mScaleFactor = 1f

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

                // do not allow scrolling during touch event
            requestDisallowInterceptTouchEvent(true)

            // get text view
            val child = getChildAt(0) as FontFitTextView

            // set up some restoration values on event of failure
            val previousMScaleFactor = mScaleFactor
            val previousColumns = child.columns

            // do scale
            mScaleFactor *= detector.scaleFactor
            Log.e("SCALE", "PREVIOUS $previousMScaleFactor")
            Log.e("SCALE", "CURRENT  $mScaleFactor")
            Log.e("SCALE", "(mScaleFactor < previousMScaleFactor) = ${(mScaleFactor < previousMScaleFactor)}")

            if (mScaleFactor < previousMScaleFactor) {
                if (child.columns == 1) {
                    Log.e("SCALE", "EXCEEDS")
                    // Don't let the object exceed maximum scale
                    mScaleFactor = previousMScaleFactor
                }
                else {
                    Log.e("SCALE", "DOES NOT EXCEED")
                }
            }

            child.columns = mScaleFactor.toInt()
            assert(child.columns != Int.MAX_VALUE)

            Log.e("SCALE", "mScaleFactor = $mScaleFactor")
            Log.e("SCALE", "new columns are ${child.columns}")
            requestDisallowInterceptTouchEvent(false)
            return if (child.DRAW()) {
                invalidate()
                true
            } else {
                mScaleFactor = previousMScaleFactor
                child.columns = previousColumns
                false
            }
        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(ev)
        super.onTouchEvent(ev)
        return true
    }

}