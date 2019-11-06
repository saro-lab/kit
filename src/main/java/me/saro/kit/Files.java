package me.saro.kit;

import me.saro.kit.functions.ThrowableFunction;
import me.saro.kit.functions.ThrowablePredicate;

import java.io.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * file util
 * @author		PARK Yong Seo
 * @since		1.0.0
 */
public class Files {

    /**
     *
     * @param file
     * @param overwrite
     * @param inputStream
     * @throws Exception
     */
    public static File createFile(File file, boolean overwrite, InputStream inputStream) throws Exception {
        if (file.exists()) {
            if (overwrite) {
                file.delete();
            } else {
                throw new IOException("create file error : already exists file : " + file.getAbsolutePath());
            }
        } else {
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            } else if (parent.isFile()) {
                throw new IOException("create file error : file exists instend of the directory : " + parent.getAbsolutePath());
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file); InputStream is = inputStream) {
            Streams.link(is, fos);
        }
        return file;
    }

    /**
     *
     * @param file
     * @param overwrite
     * @param value
     * @param charset
     * @throws Exception
     */
    public static File createFile(File file, boolean overwrite, String value, String charset) throws Exception {
        return createFile(file, overwrite, new ByteArrayInputStream(value.getBytes(charset)));
    }

    /**
     *
     * @param file
     * @param charset
     * @param process
     * @param <R>
     * @return
     * @throws Exception
     */
    public static <R> R lines(File file, String charset, ThrowableFunction<Stream<String>, R> process) throws Exception {
        return Streams.lines(new FileInputStream(file), charset, process);
    }

    /**
     *
     * @param directory
     * @return
     */
    public static List<File> list(File directory) {
        if (!directory.isDirectory()) {
            throw new RuntimeException(directory.getAbsolutePath() + " is not directory");
        }
        return List.of(directory.listFiles());
    }

    /**
     * to file name extension
     * @param filename
     * @return
     *  ex) "gif", "png", "jpg", "zip", "exe", ""
     */
    public static String toFilenameExtension(File filename) {
        String name = filename.getName();
        int pos;
        return (pos = name.lastIndexOf('.')) != -1 ? name.substring(pos + 1) : "";
    }

    /**
     * to file name extension
     * @param filename
     * @return
     *  ex) "gif", "png", "jpg", "zip", "exe", ""
     */
    public static String toFilenameExtension(String filename) {
        return toFilenameExtension(new File(filename));
    }

    /**
     *
     * @param filename
     * @param ignoreCase
     * @param filenameExtensions
     * @return
     */
    public static boolean validFilenameExtension(File filename, boolean ignoreCase, String... filenameExtensions) {
        final var extension = ignoreCase ? toFilenameExtension(filename).toLowerCase() : toFilenameExtension(filename);

        for (String filenameExtension : filenameExtensions) {
            if (extension.equals(ignoreCase ? filenameExtension.toLowerCase() : filenameExtension)) {
                return true;
            }
        }

        return false;
    }

    /**
     * get file infomation
     * @param file
     * @return
     * @throws IOException
     */
    public static BasicFileAttributes toBasicFileAttributes(File file) throws IOException {
        return java.nio.file.Files.readAttributes(file.toPath(), BasicFileAttributes.class);
    }

    /**
     * convert BasicFileAttributes to FileFilter
     * <br><br>
     * ex) // delete created before 24 hour file in /testpath <br>
     * long before24hour = DateFormat.now().addHours(-24).getTimeInMillis(); <br>
     * Files.list("/testpath").stream()<br>
     *   .filter(Files.attributesFilter(attr -> attr.creationTime().toMillis() < before24hour))<br>
     *   .forEach(File::delete);
     * @param filter
     * @return
     */
    public static Predicate<File> attributesFilter(ThrowablePredicate<BasicFileAttributes> filter) {
        return ThrowablePredicate.wrap(e -> filter.test(toBasicFileAttributes(e)));
    }
}
