package me.saro.kit;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * text util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Texts {

    final private static char[] BASE62_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    /**
     * text split<br>
     * ex) "abc,abc,,,"<br>
     * String.split : {"abc", "abc"}<br>
     * Texts.split : {"abc", "abc", "", "", ""}
     * @param text source
     * @param token token
     * @return
     */
    public static List<String> split(String text, char token) {
        char[] ca = text.toCharArray();
        int len = ca.length;
        int i = 0, p = 0;
        List rv = new ArrayList<String>();
        for (; i < len ; i++) {
            if (ca[i] == token) {
                rv.add(new String(ca, p, i - p));
                p = i + 1;
            }
        }
        rv.add(new String(ca, p, i - p));
        return rv;
    }

    /**
     * get bytes
     * @param text text
     * @param charset charset
     * @return
     */
    public static byte[] getBytes(String text, String charset) {
        return text.getBytes(Charset.forName(charset));
    }

    /**
     * return next token
     * @param text source
     * @param token token
     * @return
     */
    public static String next(String text, String token) {
        int s = text.indexOf(token);
        return s != -1 ? text.substring(s + token.length()) : "";
    }

    /**
     * return next last token
     * @param text source
     * @param token token
     * @return
     */
    public static String lastNext(String text, String token) {
        int s = text.lastIndexOf(token);
        return s != -1 ? text.substring(s + token.length()) : "";
    }

    /**
     * return prev token
     * @param text source
     * @param token token
     * @return
     */
    public static String prev(String text, String token) {
        int s = text.indexOf(token);
        return s != -1 ? text.substring(0, s) : "";
    }

    /**
     * return last prev token
     * @param text source
     * @param token token
     * @return
     */
    public static String lastPrev(String text, String token) {
        int s = text.lastIndexOf(token);
        return s != -1 ? text.substring(0, s) : "";
    }

    /**
     * return gap
     * @param text
     * @param prefix
     * @param suffix
     * @return
     */
    public static String gap(String text, String prefix, String suffix) {
        int s = text.indexOf(prefix);
        int e = text.indexOf(suffix, s + 1);
        return s != -1 && e != -1 ? text.substring(s + prefix.length(), e) : "";
    }

    /**
     * left trim text
     * @param text
     * @param spaceCharacter
     * @return
     */
    public static String rtrim(String text, char spaceCharacter) {
        char[] ca = text.toCharArray();
        for (int i = ca.length - 1 ; i >= 0 ; i--) {
            if (ca[i] != spaceCharacter) {
                return new String(ca, 0, i + 1);
            }
        }
        return "";
    }

    /**
     * left trim text
     * @param text
     * @param spaceCharacter
     * @return
     */
    public static String ltrim(String text, char spaceCharacter) {
        char[] ca = text.toCharArray();
        int length = ca.length;
        for (int i = 0; i < length ; i++) {
            if (ca[i] != spaceCharacter) {
                return new String(ca, i, length - i);
            }
        }
        return "";
    }

    /**
     * null value logic
     * @param datas datas
     * @return
     */
    @SafeVarargs
    public static String nvl(String... datas) {
        for (String t : datas) {
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    /**
     * empty value logic
     * @param datas datas
     * @return
     */
    public static String evl(String... datas) {
        for (String val : datas) {
            if (val != null && !val.isEmpty()) {
                return val;
            }
        }
        return null;
    }

    /**
     * blank value logic
     * @param datas datas
     * @return
     */
    public static String bvl(String... datas) {
        for (String val : datas) {
            if (val != null && !val.matches("[\\s]*")) {
                return val;
            }
        }
        return null;
    }

    /**
     * create random string
     * @param mold
     * base mold for create random string
     * @param len
     * create langth
     * @return
     * random string
     */
    public static String createRandomString(char[] mold, int len) {
        char[] rv = new char[len];
        int charLen = mold.length;

        for (int i = 0 ;i < len ; i++) {
            rv[i] = mold[(int)(Math.random() * charLen)];
        }

        return new String(rv);
    }

    /**
     * create random string
     * @param mold
     * base mold for create random string
     * @param min
     * min length
     * @param max
     * max length
     * @return
     * create random string
     * min &lt;= return value &lt;= max
     */
    public static String createRandomString(char[] mold, int min, int max) {
        return createRandomString(mold, (int)random(min, max));
    }

    /**
     * create random base62 string
     * <br>
     * base62 : [ A-Z a-z 0-9 ]
     * @param min
     * min length
     * @param max
     * max length
     * @return
     *
     */
    public static String createRandomBase62String(int min, int max) {
        return createRandomString(BASE62_CHARS, (int)random(min, max));
    }

    /**
     * get random
     * @param min
     * min length
     * @param max
     * max length
     * @return
     * min &lt;= return value &lt;= max
     */
    public static long random(long min, long max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            throw new IllegalArgumentException("'lessThen' have to over the value then 'min'");
        }
        return min + (int)(Math.random() * ((max + 1) - min));
    }
}
