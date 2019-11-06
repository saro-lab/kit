package me.saro.kit.hashs;

import me.saro.kit.bytes.Bytes;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class Hashs {
    /**
     * to Hash
     * @param hashAlgorithm
     * @param data
     * @return
     * @since 1.0.0
     */
    public static byte[] toHash(HashAlgorithm hashAlgorithm, byte[] data) {
        try {
            return MessageDigest.getInstance(hashAlgorithm.value()).digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * to Hash
     * @param hashAlgorithm
     * @param text
     * @param charset
     * @return
     * @since 1.0.0
     */
    public static byte[] toHash(HashAlgorithm hashAlgorithm, String text, String charset) {
        return toHash(hashAlgorithm, text.getBytes(Charset.forName(charset)));
    }

    /**
     * to Hash
     * <br>
     * charset is UTF-8
     * @param hashAlgorithm
     * @param text
     * @return
     * @since 1.0.0
     */
    public static byte[] toHash(HashAlgorithm hashAlgorithm, String text) {
        return toHash(hashAlgorithm, text.getBytes(Charset.forName("UTF-8")));
    }

    /**
     *
     * @param hashAlgorithm
     * @param text
     * @return
     * @since 1.0.0
     */
    public static String toHashHex(HashAlgorithm hashAlgorithm, String text, String charset) {
        try {
            return Bytes.toHex(toHash(hashAlgorithm, text.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @since 1.0.0
     * @param hashAlgorithm
     * @param text
     * @return
     */
    public static String toHashHex(HashAlgorithm hashAlgorithm, String text) {
        return Bytes.toHex(toHash(hashAlgorithm, text.getBytes(Charset.forName("UTF-8"))));
    }

    /**
     *
     * @param hashAlgorithm
     * @param text
     * @return
     * @since 1.0.0
     */
    public static String toHashBase64(HashAlgorithm hashAlgorithm, String text, String charset) {
        try {
            return Bytes.encodeBase64String(toHash(hashAlgorithm, text.getBytes(charset)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @since 1.0.0
     * @param hashAlgorithm
     * @param text
     * @return
     */
    public static String toHashBase64(HashAlgorithm hashAlgorithm, String text) {
        return Bytes.encodeBase64String(toHash(hashAlgorithm, text.getBytes(Charset.forName("UTF-8"))));
    }
}
