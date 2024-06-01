package me.saro.kit

import java.net.URI
import java.nio.file.FileSystem
import java.nio.file.FileSystems

class FileUtils {
    companion object {


        @JvmStatic
        fun openInJar(clazz: Class<*>): FileSystem =
            FileSystems.newFileSystem(URI.create("jar:file:${clazz.protectionDomain.codeSource.location.toURI().path}"), mutableMapOf<String, Any>())

        @JvmStatic
        fun openInJar(jarPath: String): FileSystem =
            FileSystems.newFileSystem(URI.create("jar:file:$jarPath"), mutableMapOf<String, Any>())
    }
}