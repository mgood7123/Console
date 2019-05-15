@file:Suppress("unused")

package com.example.broadcastConsole

@JvmName("TOSTRINGBUILDER")
fun String.toStringBuilder(): StringBuilder {
    return StringBuilder(this)
}

fun String?.toStringBuilder(): StringBuilder? = this?.toStringBuilder()

@JvmName("TOSTRINGBUILDERCAPACITY")
fun String.toStringBuilder(capacity: Int): StringBuilder {
    return StringBuilder(capacity).append(this)
}

fun String?.toStringBuilder(capacity: Int): StringBuilder? = this?.toStringBuilder(capacity)
