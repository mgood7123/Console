@file:Suppress("unused")

package com.example.broadcastConsole

import android.arch.lifecycle.LifecycleOwner
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.system.Os
import android.view.View

import kotlinx.android.synthetic.main.content_console.*
import java.io.BufferedReader
import java.io.InputStreamReader
import android.support.v7.widget.RecyclerView
import android.widget.Adapter


@Suppress("MemberVisibilityCanBePrivate")
class Console : AppCompatActivity() {

//    private var recycler: RecyclerView? = null
//    private var manager: RecyclerView.LayoutManager? = null
//    private var adapter: Adapter? = null
//    private val list = ArrayList<String>()

    var console: ConsoleSession? = null

    fun clear(@Suppress("UNUSED_PARAMETER") view: View) = console?.clear()
    fun exe(@Suppress("UNUSED_PARAMETER") view: View) = try {
        val cmd = "stty -a"
        val cmdwhite = "printf \\\\e[?5h"
        val cmdblack = "printf \\\\e[?5l"
        console?.println("executing command: $cmd")
        val p = ProcessBuilder("myCommand", "myArg")
//        val pro = p.start()
        Runtime.getRuntime().exec("stty -a").also {
            it.inputStream.bufferedReader().useLines { it.forEach { console?.println("stdout: $it") } }
            it.errorStream.bufferedReader().useLines { it.forEach { console?.println("stderr: $it") } }
        }
    } catch (e: InterruptedException) {
        e.printStackTrace()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
//        Grid.columnCount = 10
//        Grid.rowCount = 10
//        Recycler
//        Grid.cell[0] = "h"
//        recycler = RecyclerView
//        recycler?.setHasFixedSize(true)
//        manager = GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)
//        recycler?.layoutManager = manager
//        list
//        adapter = Adapter(list, this)

//        textView.setTextIsSelectable(true)
//        if (console == null) console = ConsoleSession(this)
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
        console?.println("onResume")

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
