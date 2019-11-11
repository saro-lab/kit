package me.saro.kit;

import java.util.ArrayList;
import java.util.List;

/**
 * list util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Lists {
    /**
     * This method, unlike "Arrays.asList", can editable the returned result.
     * @param args element arguments or array
     * @param <T> template
     * @return editable list (ArrayList<T>)
     */
    @SafeVarargs
    public static <T> List<T> asList(T... args) {
        List<T> list = new ArrayList<>();
        if (args != null) {
            for (T t : args) {
                list.add(t);
            }
        }
        return list;
    }
}
