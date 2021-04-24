package me.saro.test.legacy.webs

import java.io.InputStream

fun interface WebReader {
    @Throws(Exception::class)
    fun read(status: Int, headers: Map<String?, List<String?>?>?, `is`: InputStream?)
}