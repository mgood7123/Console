package com.example.broadcastConsole

import android.util.Log
import com.example.broadcastConsole.grid.Terminal
import com.utils.`class`.extensions.ThreadWaitForCompletion
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File

@Suppress("MemberVisibilityCanBePrivate")
class ConsoleSession(
    val output: Terminal.FontFitTextView,
    val screen: Terminal.Zoomable
) {

    inner class TTY() {
        val stdin = ""
    }
    var session_id = mutableListOf(0)

    var config: RealmConfiguration? = null

    var session = "session"

    var consoleRealm: Realm? = null

    var initialized = false

    fun CharSequence.update() = this.toString().update()
    fun CharSequence.updateOverwrite() = this.toString().updateOverwrite()

    fun String.update() {
        ThreadWaitForCompletion(output.mainThread!!) {
            output.append(this)
//            scrollDown(console, console.scrollView2)
        }
    }

    fun String.updateOverwrite() {
        ThreadWaitForCompletion(output.mainThread!!) {
            output.text = this
//            scrollDown(console, console.scrollView2)
        }
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
        Log.i("REALM", "load started")
        // print cannot be used in this call
        if (!initialized) Realm.init(output.mainThread?.applicationContext)
        for (it in session_id) {
            val path = "${output.mainThread?.filesDir}/$session$it"
            if (File(path).exists()) {
                Log.i("REALM", "configuration exists")
                config = RealmConfiguration.Builder()
                    .name("$session$it")
                    .schemaVersion(ConsoleRealmObjectVersion)
                    .migration(MigrateConsole())
                    .build()

                Realm.setDefaultConfiguration(config!!)
                consoleRealm = Realm.getDefaultInstance()
                consoleRealmObjectInstance(consoleRealm!!).stdout.updateOverwrite()
                initialized = true
                Log.i("REALM", "configuration initialized: $session$it at $path")
                break
            }
        }
        if (config == null) {
            Log.i("REALM", "configuration does not exist")
            config = RealmConfiguration.Builder()
                .name("$session${session_id[0]}")
                .schemaVersion(ConsoleRealmObjectVersion)
                .build()
            Realm.setDefaultConfiguration(config!!)
            consoleRealm = Realm.getDefaultInstance()
            consoleRealmObjectInstance(consoleRealm!!).stdout.updateOverwrite()
            initialized = true
            Log.i("REALM", "configuration created: $session${session_id[0]} at ${output.mainThread?.filesDir.toString()}/$session${session_id[0]}")
        }
        Log.i("REALM", "load finished")
    }

    fun save() {
        Log.i("REALM", "save started")
        val consoleRealmObject = consoleRealmObjectInstance(consoleRealm!!)
        consoleRealm?.beginTransaction()
        Log.i("REALM", "saving '${output.text.toString()}' to configuration")
        consoleRealmObject.stdout = output.text.toString()
        consoleRealm?.commitTransaction()
        Log.i("REALM", "save finished")
    }

    fun unload() {
        Log.i("REALM", "unload started")
        consoleRealm?.close()
        initialized = false
        Log.i("REALM", "unload finished")
    }

}