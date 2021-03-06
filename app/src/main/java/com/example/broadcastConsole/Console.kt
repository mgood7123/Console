@file:Suppress("unused")

package com.example.broadcastConsole

import android.os.Bundle
import android.os.SystemClock.sleep
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.example.broadcastConsole.grid.Terminal
import kotlinx.android.synthetic.main.activity_console.*
import kotlin.concurrent.thread


@Suppress("MemberVisibilityCanBePrivate")
class Console : AppCompatActivity() {

//    private var recycler: RecyclerView? = null
//    private var manager: RecyclerView.LayoutManager? = null
//    private var adapter: Adapter? = null
//    private val list = ArrayList<String>()

    var console: ConsoleSession? = null

    fun clear(@Suppress("UNUSED_PARAMETER") view: View) = console?.clear()
    fun exe(@Suppress("UNUSED_PARAMETER") view: View) = try {
        for (i in 0..250) {
            console?.println("$i")
            sleep(250)
        }
//        val cmd = "stty -a"
//        val cmdwhite = "printf \\\\e[?5h"
//        val cmdblack = "printf \\\\e[?5l"
//        console?.println("executing command: $cmd")
//        val p = ProcessBuilder("myCommand", "myArg")
////        val pro = p.start()
//        Runtime.getRuntime().exec("stty -a").also {
//            it.inputStream.bufferedReader().useLines { it.forEach { console?.println("stdout: $it") } }
//            it.errorStream.bufferedReader().useLines { it.forEach { console?.println("stderr: $it") } }
//        }
    } catch (e: InterruptedException) {
        e.printStackTrace()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)

//        textView.setTextIsSelectable(true)
        val t = Terminal().termView(this)
        (include as ViewGroup).addView(t)
        if (console == null) console = ConsoleSession(t.getChildAt(0) as Terminal.FontFitTextView, t)
//        lifecycle.addObserver(ConsoleUpdater(this))
        console?.println("onCreate")
    }

    override fun onStart() {
        super.onStart()
        console?.println("onStart")
    }

    override fun onRestart() {
        super.onRestart()
        console?.println("onRestart")
    }

    override fun onResume() {
        super.onResume()
        console?.println("sleeping for 10 seconds in a thread")
        thread {
            sleep(10000)
            console?.println("onResume")
            console?.println("onResume appended")
        }
    }

    override fun onPause() {
        super.onPause()
        console?.println("onPause")
    }

    override fun onStop() {
        super.onStop()
        console?.println("onStop")
        // this is also called when entering/exiting Pop-Up view on samsung devices
    }

    override fun onDestroy() {
        super.onDestroy()
        console?.println("onDestroy")
        // this is also called when entering/exiting Pop-Up view on samsung devices
    }
}
