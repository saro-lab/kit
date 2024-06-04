package me.saro.kit

import java.io.File
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files.walk
import java.nio.file.Path

class FileKit {
    companion object {
        @JvmStatic
        fun openInJar(clazz: Class<*>, directoryPath: String, action: (Path) -> Unit) {
            val jarPath = clazz.protectionDomain.codeSource.location.toURI().path
            if (jarPath.endsWith(".jar")) { // in jar
                FileSystems.newFileSystem(URI.create("jar:file:$jarPath"), mutableMapOf<String, Any>()).use {
                    walk(it.getPath(directoryPath)).forEach(action)
                }
            } else { // ide mode
                File(clazz.getResource(directoryPath).toURI()).listFiles().forEach { file -> action(file.toPath()) }
            }
        }
    }
}
