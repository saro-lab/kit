package me.saro.kit;

import java.util.List;

/**
 * primitive object util
 * @author PARK Yong Seo
 * @since 1.0.0
 */
public class Primitive {
    /**
     * List&lt;Short&gt; to short[]
     * @param list
     * @return
     */
    public static short[] toShortArray(List<Short> list) {
        short[] rv = new short[list.size()];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = list.get(i);
        }
        return rv;
    }

    /**
     * List&lt;Integer&gt; to int[]
     * @param list
     * @return
     */
    public static int[] toIntArray(List<Integer> list) {
        int[] rv = new int[list.size()];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = list.get(i);
        }
        return rv;
    }

    /**
     * List&lt;Long&gt; to long[]
     * @param list
     * @return
     */
    public static long[] toLongArray(List<Long> list) {
        long[] rv = new long[list.size()];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = list.get(i);
        }
        return rv;
    }

    /**
     * List&lt;Float&gt; to float[]
     * @param list
     * @return
     */
    public static float[] toFloatArray(List<Float> list) {
        float[] rv = new float[list.size()];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = list.get(i);
        }
        return rv;
    }

    /**
     * List&lt;Double&gt; to double[]
     * @param list
     * @return
     */
    public static double[] toDoubleArray(List<Double> list) {
        double[] rv = new double[list.size()];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = list.get(i);
        }
        return rv;
    }

    /**
     * List&lt;Byte&gt; to byte[]
     * @param array
     * @return
     */
    public static byte[] toPrimitive(Byte[] array) {
        byte[] rv = new byte[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * Short[] to short[]
     * @param array
     * @return
     */
    public static short[] toPrimitive(Short[] array) {
        short[] rv = new short[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * Integer[] to int[]
     * @param array
     * @return
     */
    public static int[] toPrimitive(Integer[] array) {
        int[] rv = new int[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * Long[] to long[]
     * @param array
     * @return
     */
    public static long[] toPrimitive(Long[] array) {
        long[] rv = new long[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * Float[] to float[]
     * @param array
     * @return
     */
    public static float[] toPrimitive(Float[] array) {
        float[] rv = new float[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * Double[] to double[]
     * @param array
     * @return
     */
    public static double[] toPrimitive(Double[] array) {
        double[] rv = new double[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * byte[] to Byte[]
     * @param array
     * @return
     */
    public static Byte[] toUnPrimitive(byte[] array) {
        Byte[] rv = new Byte[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * short[] to Short[]
     * @param array
     * @return
     */
    public static Short[] toUnPrimitive(short[] array) {
        Short[] rv = new Short[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * int[] to Integer[]
     * @param array
     * @return
     */
    public static Integer[] toUnPrimitive(int[] array) {
        Integer[] rv = new Integer[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * long[] to Long[]
     * @param array
     * @return
     */
    public static Long[] toUnPrimitive(long[] array) {
        Long[] rv = new Long[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * float[] to Float[]
     * @param array
     * @return
     */
    public static Float[] toUnPrimitive(float[] array) {
        Float[] rv = new Float[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }

    /**
     * double[] to Double[]
     * @param array
     * @return
     */
    public static Double[] toUnPrimitive(double[] array) {
        Double[] rv = new Double[array.length];
        for (int i = 0 ; i < rv.length ; i++) {
            rv[i] = array[i];
        }
        return rv;
    }
}
