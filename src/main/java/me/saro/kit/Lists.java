package me.saro.kit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
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
    public static <T> List<T> asList(T... args) {
        List<T> list = new ArrayList<>();
        if (args != null) {
            for (T t : args) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * merge lists
     * @param collections
     * @param <T>
     * @return
     */
    public static <T> Collection<T> merge(Collection<T>... collections) {
        Collection<T> list = new ArrayList<T>();
        for (Collection<T> node : collections) {
            list.addAll(node);
        }
        return list;
    }

    /**
     * merge arrays
     * @param arrays
     * @param <T>
     * @return
     */
    public static <T> T[] merge(T[]... arrays) {
        int sum = 0;
        for (T[] arr : arrays) {
            sum += arr.length;
        }
        T[] rv = (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), sum);

        int offset = 0;
        for (T[] arr : arrays) {
            System.arraycopy(arr, 0, rv, offset, arr.length);
            offset += arr.length;
        }

        return rv;
    }
}
