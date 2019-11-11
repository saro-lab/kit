package me.saro.kit;

import me.saro.kit.functions.ThrowableFunction;

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * stream util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Streams {

    private static int BUFSIZE = 8192;

    /**
     * input stream to string
     * @param is input stream
     * @param charset charset
     * @return
     * @throws IOException
     */
    public static String toString(InputStream is, String charset) throws IOException {
        try (is ; var isr = new InputStreamReader(is, charset)) {
            int len;
            var sb = new StringBuilder(BUFSIZE);
            var buf = new char[BUFSIZE];
            while ((len = isr.read(buf, 0, BUFSIZE)) > -1) {
                sb.append(buf, 0, len);
            }
            return sb.toString();
        }
    }

    /**
     * input stream bind to byte array
     * @param src input stream
     * @param descBytes byte array will bind
     * @param descBytesOffset bind offset
     * @throws IOException
     */
    public static void bind(InputStream src, byte[] descBytes, int descBytesOffset) throws IOException {
        try (src) {
            int len;
            int idx = descBytesOffset;
            var buf = new byte[BUFSIZE];
            while ((len = src.read(buf, 0, BUFSIZE)) > -1) {
                System.arraycopy(buf, 0, descBytes, idx, len);
                idx += len;
            }
        }
    }

    /**
     * link input stream to output stream
     * @param is input stream
     * @param os output stream
     * @throws IOException
     */
    public static void link(InputStream is, OutputStream os) throws IOException {
        try (is ; os) {
            int len;
            var buf = new byte[BUFSIZE];
            while ((len = is.read(buf, 0, BUFSIZE)) > -1) {
                os.write(buf, 0, len);
            }
            os.flush();
        }
    }

    /**
     * input stream to lines in the process function
     * @param is source input stream
     * @param charset charset
     * @param process (Stream&lt;String&gt; line): R
     * @param <R> return type
     * @return
     * @throws Exception
     */
    public static <R> R lines(InputStream is, String charset, ThrowableFunction<Stream<String>, R> process) throws Exception {
        try (is ; var isr = new InputStreamReader(is, charset) ; var br = new BufferedReader(isr)) {
            return process.apply(br.lines());
        }
    }

    /**
     * iterable to stream
     * @param iterable source
     * @param parallel isParallel
     * @param <T> template
     * @return
     */
    public static <T> Stream<T> toStream(Iterable<T> iterable, boolean parallel) {
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    /**
     * enumeration to stream
     * @param enumeration source
     * @param parallel isParallel
     * @param <T> template
     * @return
     */
    public static <T> Stream<T> toStream(Enumeration<T> enumeration, boolean parallel) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<T>() {
            public T next() {
                return enumeration.nextElement();
            }
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }
        }, Spliterator.ORDERED), parallel);
    }
}

