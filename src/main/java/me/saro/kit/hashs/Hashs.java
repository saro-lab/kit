package me.saro.kit.hashs;

import me.saro.kit.Texts;
import me.saro.kit.bytes.Bytes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Hashs {

    /**
     * to Hash
     * @param hashAlgorithm hashAlgorithm
     * @param data data
     * @return
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
     * @param hashAlgorithm hashAlgorithm
     * @param text text
     * @param charset charset
     * @return
     */
    public static byte[] toHash(HashAlgorithm hashAlgorithm, String text, String charset) {
        return toHash(hashAlgorithm, Texts.getBytes(text, charset));
    }

    /**
     * to Hash<br>
     * charset is UTF-8
     * @param hashAlgorithm hashAlgorithm
     * @param text text
     * @return
     */
    public static byte[] toHash(HashAlgorithm hashAlgorithm, String text) {
        return toHash(hashAlgorithm, Texts.getBytes(text, "UTF-8"));
    }

    /**
     * toHashHex
     * @param hashAlgorithm hashAlgorithm
     * @param text text
     * @return
     */
    public static String toHashHex(HashAlgorithm hashAlgorithm, String text, String charset) {
        return Bytes.toHex(toHash(hashAlgorithm, Texts.getBytes(text, charset)));
    }

    /**
     * toHashHex
     * @param hashAlgorithm hashAlgorithm
     * @param text text
     * @return
     */
    public static String toHashHex(HashAlgorithm hashAlgorithm, String text) {
        return Bytes.toHex(toHash(hashAlgorithm, Texts.getBytes(text, "UTF-8")));
    }

    /**
     * toHashBase64
     * @param hashAlgorithm hashAlgorithm
     * @param text text
     * @return
     * @since 1.0.0
     */
    public static String toHashBase64(HashAlgorithm hashAlgorithm, String text, String charset) {
        return Bytes.encodeBase64String(toHash(hashAlgorithm, Texts.getBytes(text, charset)));
    }

    /**
     * toHashBase64
     * @param hashAlgorithm hashAlgorithm
     * @param text text
     * @return
     */
    public static String toHashBase64(HashAlgorithm hashAlgorithm, String text) {
        return Bytes.encodeBase64String(toHash(hashAlgorithm, Texts.getBytes(text, "UTF-8")));
    }
}
