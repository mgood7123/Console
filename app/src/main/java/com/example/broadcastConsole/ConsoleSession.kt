package com.example.broadcastConsole

import android.content.Context
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.content_console.*
import java.io.File
import kotlin.concurrent.thread

@Suppress("MemberVisibilityCanBePrivate")
class ConsoleSession(
    private val console: Console
) {

    inner class TTY() {
        val stdin = ""
    }
    var session_id = mutableListOf(0)

    var config: RealmConfiguration? = null

    var session = "session"

    var consoleRealm: Realm? = null

    var initialized = false

    fun String.update() {
//        console.textView.append(this)
//        scrollDown(console, console.scrollView2)
    }

    fun String.updateOverwrite() {
//        console.textView.text = this
//        scrollDown(console, console.scrollView2)
    }

    fun clear() {
        load()
        "".updateOverwrite()
        save()
        unload()
    }

    // loading on every print may produce large overhead on print intensive tasks,
    // such as printing a large document lne by line, heavy debug output
    fun print(message: String) {
        load()
        message.update()
        save()
        unload()
    }

    fun println(message: String) = print(message + "\n")

    fun load() {
        // print cannot be used in this call
        if (!initialized) Realm.init(console.applicationContext)
        for (it in session_id) {
            val path = "${console.filesDir}/$session$it"
            if (File(path).exists()) {
                config = RealmConfiguration.Builder()
                    .name("$session$it")
                    .schemaVersion(ConsoleRealmObjectVersion)
                    .migration(MigrateConsole())
                    .build()

                Realm.setDefaultConfiguration(config!!)
                consoleRealm = Realm.getDefaultInstance()
                consoleRealmObjectInstance(consoleRealm!!).stdout.updateOverwrite()
                initialized = true
//                println("configuration exists")
//                println("configuration initialized: $session$it at $path")
                break
            }
        }
        if (config == null) {
            config = RealmConfiguration.Builder()
                .name("$session${session_id[0]}")
                .schemaVersion(ConsoleRealmObjectVersion)
                .build()
            Realm.setDefaultConfiguration(config!!)
            consoleRealm = Realm.getDefaultInstance()
            consoleRealmObjectInstance(consoleRealm!!).stdout.updateOverwrite()
            initialized = true
//            println("configuration does not exist")
//            println("configuration created: $session${session_id[0]} at ${filesDir.toString()}/$session${session_id[0]}")
        }
    }

    fun save() {
        val consoleRealmObject = consoleRealmObjectInstance(consoleRealm!!)
        consoleRealm?.beginTransaction()
        consoleRealmObject.stdout = ""//console.textView.text.toString()
        consoleRealm?.commitTransaction()
    }

    fun unload() {
        consoleRealm?.close()
        initialized = false
    }

}