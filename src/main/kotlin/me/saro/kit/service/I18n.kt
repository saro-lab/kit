package me.saro.kit.service

import java.io.File
import java.util.logging.Logger
import java.util.stream.Stream

class I18n private constructor(
    private val map: Map<String, Map<String, String>>
) {
    operator fun get(lang: String, code: String): String {
        val loLang = lang.lowercase().split(';')

        val node = map[code]
            ?: return code

        return loLang.asSequence()
            .filter { it.isNotBlank() }
            .map { node[it.trim()] }
            .firstOrNull { it != null }
            ?: return node.firstNotNullOfOrNull { it.value } ?: code
    }

    companion object {
        private val log = Logger.getLogger(I18n::class.qualifiedName)

        @JvmStatic
        fun load(fileOrDirectory: File): I18n {
            if (!fileOrDirectory.exists() || !fileOrDirectory.canRead()) {
                throw IllegalArgumentException("I18n["+fileOrDirectory.absolutePath+"] file is not exists or not file or not readable")
            }

            if (fileOrDirectory.isFile) {
                return load(fileOrDirectory.readLines(Charsets.UTF_8))
            } else if (fileOrDirectory.isDirectory) {
                val files: Array<File> = fileOrDirectory.listFiles { it: File -> it.isFile && it.canRead() && it.name.endsWith(".yml") } ?: emptyArray()
                if (files.isEmpty()) {
                    throw IllegalArgumentException("I18n["+fileOrDirectory.absolutePath+"] .yml file is not exists in directory")
                }
                return load(files)
            } else {
                throw IllegalArgumentException("I18n["+fileOrDirectory.absolutePath+"] file is not file or directory")
            }
        }

        fun load(files: Array<File>): I18n {
            return load(Stream.of(*files).flatMap { it.readLines(Charsets.UTF_8).stream() })
        }

        @JvmStatic
        fun load(messages: String): I18n {
            return load(messages.lines())
        }

        @JvmStatic
        fun load(messagesLine: List<String>): I18n {
            return load(messagesLine.stream())
        }

        @JvmStatic
        fun load(messagesLine: Stream<String>): I18n {

            val map: LinkedHashMap<String, LinkedHashMap<String, String>> = linkedMapOf()
            var node: LinkedHashMap<String, String>? = null

            messagesLine
                .filter { it.isNotBlank() }
                .forEach { line: String ->
                    if (!(line[0] == ' ' || line[0] == '\t')) {
                        val nowCode = line.trim()
                        if (!nowCode.endsWith(':')) {
                            throw IllegalArgumentException("I18n code must end with ':' : $nowCode")
                        }
                        val code = nowCode.substring(0, nowCode.length - 1).trim()

                        node = map[code]

                        if (node == null) {
                            node = linkedMapOf()
                            map[code] = node!!
                        }
                        return@forEach
                    }

                    if (node == null) {
                        throw IllegalArgumentException("I18n code is not defined: $line")
                    }

                    val iof = line.indexOf(':')
                    if (iof == -1) {
                        throw IllegalArgumentException("I18n message must have langCode ex) en: hello : $line")
                    }

                    val langCode = line.substring(0, iof).trim()
                    val langValue = line.substring(iof + 1).trim()

                    if (langCode.isBlank() || langValue.isBlank()) {
                        return@forEach
                    }

                    node!![langCode] = langValue
                }

            return I18n(map.filter { it.value.isNotEmpty() })
        }
    }
}
