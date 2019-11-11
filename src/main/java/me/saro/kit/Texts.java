package me.saro.kit;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * text util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Texts {

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
        var ca = text.toCharArray();
        int len = ca.length;
        int i = 0, p = 0;
        var rv = new ArrayList<String>();
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
        return text.substring(text.indexOf(token) + token.length());
    }

    /**
     * return next last token
     * @param text source
     * @param token token
     * @return
     */
    public static String lastNext(String text, String token) {
        return text.substring(text.lastIndexOf(token) + token.length());
    }

    /**
     * return prev token
     * @param text source
     * @param token token
     * @return
     */
    public static String prev(String text, String token) {
        return text.substring(0, text.indexOf(token));
    }

    /**
     * return last prev token
     * @param text source
     * @param token token
     * @return
     */
    public static String lastPrev(String text, String token) {
        return text.substring(0, text.lastIndexOf(token));
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
            if (val != null && !val.isBlank()) {
                return val;
            }
        }
        return null;
    }

}
