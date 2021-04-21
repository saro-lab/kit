package me.saro.kit

import me.saro.kit.legacy.ThrowableFunction
import me.saro.kit.legacy.ThrowablePredicate
import me.saro.kit.legacy.ThrowablePredicate.Companion.wrap
import java.io.*
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import java.util.function.Predicate
import java.util.stream.Stream

/**
 * file util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
open class Files {
    companion object {
        /**
         * create file
         * @param file
         * @param overwrite
         * @param inputStream
         * @throws Exception
         */


        @JvmStatic
        fun create(file: File, overwrite: Boolean, inputStream: InputStream): File? {
            if (file.exists()) {
                if (overwrite) {
                    file.delete()
                } else {
                    throw IOException("create file error : already exists file : " + file.absolutePath)
                }
            } else {
                val parent = file.parentFile
                if (!parent.exists()) {
                    parent.mkdirs()
                } else if (parent.isFile) {
                    throw IOException("create file error : file exists instend of the directory : " + parent.absolutePath)
                }
            }
            FileOutputStream(file).use { fos -> inputStream.use { `is` -> Streams.link(`is`, fos) } }
            return file
        }

        /**
         * create file
         * @param file
         * @param overwrite
         * @param value
         * @param charset
         * @throws Exception
         */
        @JvmStatic
        fun create(file: File, overwrite: Boolean, value: String, charset: Charset?): File? {
            return create(file, overwrite, ByteArrayInputStream(value.toByteArray(charset!!)))
        }

        /**
         * text file to lines in the process function
         * @param file file
         * @param charset charset
         * @param process (Stream&lt;String&gt; line): R
         * @param <R> return type
         * @return
         * @throws Exception
        </R> */
        @JvmStatic
        fun <R> readLines(file: File?, charset: Charset?, process: ThrowableFunction<Stream<String?>?, R>?): R {
            FileInputStream(file).use { fis -> return Streams.lines(fis, charset, process) }
        }

        /**
         * list in the directory
         * @param directory directory file
         * @return
         */
        @JvmStatic
        fun list(directory: File): List<File?>? {
            if (!directory.isDirectory) {
                throw RuntimeException(directory.absolutePath + " is not directory")
            }
            return Arrays.asList(*directory.listFiles())
        }

        /**
         * to file name extension
         * @param file filename
         * @return
         * ex) "gif", "png", "jpg", "zip", "exe", ""
         */
        @JvmStatic
        fun toFilenameExtension(file: File): String {
            val name = file.name
            var pos: Int
            return if (name.lastIndexOf('.').also { pos = it } != -1) name.substring(pos + 1) else ""
        }

        /**
         * to file name extension
         * @param filename filename
         * @return
         * ex) "gif", "png", "jpg", "zip", "exe", ""
         */
        @JvmStatic
        fun toFilenameExtension(filename: String?): String? {
            return toFilenameExtension(File(filename))
        }

        /**
         * valid filename extension
         * @param file file
         * @param ignoreCase is ignore case
         * @param filenameExtensions filename extensions
         * @return
         */
        @JvmStatic
        fun validFilenameExtension(file: File, ignoreCase: Boolean, vararg filenameExtensions: String): Boolean {
            val extension = if (ignoreCase) toFilenameExtension(file).toLowerCase() else toFilenameExtension(file)
            for (filenameExtension in filenameExtensions) {
                if (extension == if (ignoreCase) filenameExtension.toLowerCase() else filenameExtension) {
                    return true
                }
            }
            return false
        }

        /**
         * get file infomation
         * @param file file
         * @return
         * @throws IOException
         */
        @JvmStatic
        fun toBasicFileAttributes(file: File): BasicFileAttributes? {
            return Files.readAttributes(file.toPath(), BasicFileAttributes::class.java)
        }

        /**
         * convert BasicFileAttributes to FileFilter<br></br><br></br>
         * ex) // delete created before 24 hour file in /testpath <br></br>
         * long before24hour = DateFormat.now().addHours(-24).getTimeInMillis(); <br></br>
         * Files.list("/testpath").stream()<br></br>
         * .filter(Files.attributesFilter(attr -> attr.creationTime().toMillis() < before24hour))<br></br>
         * .forEach(File::delete);
         * @param filter filter
         * @return
         */
        @JvmStatic
        fun attributesFilter(filter: ThrowablePredicate<BasicFileAttributes?>): Predicate<File>? {
            return wrap { e: File ->
                filter.test(
                    toBasicFileAttributes(e)
                )
            }
        }

        /**
         * to create times millis
         * @param file
         * @return
         * @throws IOException
         */
        @JvmStatic
        fun toCreationTimeMillis(file: File): Long {
            val attr = toBasicFileAttributes(file)
            return attr?.creationTime()?.toMillis() ?: 0L
        }
    }

}