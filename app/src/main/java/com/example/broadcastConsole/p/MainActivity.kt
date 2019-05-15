package com.example.broadcastConsole.p

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock.sleep
import android.support.v7.widget.GridLayoutManager
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*
import com.example.broadcastConsole.R
import kotlin.concurrent.thread


@Suppress("MemberVisibilityCanBePrivate")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 128+128      failed to compile with HEAVY GC and constant memory overflow (256/256MB)
        // 128+64       compiles with GC at end, renders successfully
        // 128+64+32    failed to compile, crashed
        // 128+64+16    failed to compile, crashed
        // 128+64+8     failed to compile, crashed
        // 128+64+4     compiles with GC at end, renders successfully
        // 128+64+4+2   compiles with GC at end, renders successfully
        // 128+64+4+2+1 failed to compile, crashed
        val scale: Int = 8


        u(scale, scale)
        // smallest without clipping: 72x82
        // smallest that JUST almost clips: 72x84
    }

    fun u(h: Int, w: Int) {
        val manager = GridLayoutManager(applicationContext, w)
        val adapter = RecyclerAdapter(h, manager)
        recycler.adapter = adapter
        recycler.layoutManager = manager
    }

    override fun onResume() {
        super.onResume()
//        recycler.post { u(32, 32) }
//        recycler.post { sleep(5000) }
//        recycler.post { u(64, 64) }
        recycler.post { Runtime.getRuntime().exec("uiautomator dump") }
    }

    override fun onPause() {
        super.onPause()
    }
}