package me.saro.kit;

import java.util.Arrays;

public class Numbers {

    /**
     * zerofill
     * @param number
     * @param length
     * @return
     */
    public static String zerofill(String number, int length) {
        if (length < 1) {
            throw new IllegalArgumentException("length must more then 1");
        }
        if (!number.matches("[\\d]+")) {
            throw new IllegalArgumentException("number must unsigned integer([0-9]+)");
        }
        int needFillSize = length - number.length();

        if (needFillSize == 0) {
            return number;
        } else if (needFillSize > 0) {
            char[] fills = new char[needFillSize];
            Arrays.fill(fills, '0');
            return new String(fills) + number;
        } else {
            throw new IllegalArgumentException("["+number+"] is over length then "+length);
        }
    }

    /**
     * zerofill
     * @param val
     * @param length
     * @return
     */
    public static String zerofill(long val, int length) {
        return zerofill(Long.toString(val), length);
    }

    /**
     * zerofill
     * @param val
     * @param length
     * @return
     */
    public static String zerofill(int val, int length) {
        return zerofill(Integer.toString(val), length);
    }

    /**
     * normalize number
     * 1,000 -> 1000
     * -000123 -> -123
     * +0001,234 -> 1234
     * 000123 -> 123
     * 123.00 -> 123
     * " 123,456" -> 123456
     * @param number
     * @return
     */
    public static String norNumber(String number) {
        String no = number.trim().replace(",", "").replaceFirst("\\.[0]+", "");
        boolean m = false;
        switch (no.charAt(0)) {
            case '-' : m = true;
            case '+' : no = no.substring(1);
        }
        int zeroIndex = 0;
        for (char ch : no.toCharArray()) {
            if (ch == '0') {
                zeroIndex++;
            } else {
                break;
            }
        }
        return (m ? "-" : "") + (zeroIndex == 0 ? no : no.substring(zeroIndex));
    }

    /**
     * String to double
     * @param doubleValue
     * @return
     */
    public static double parseDouble(String doubleValue) {
        return Double.parseDouble(norNumber(doubleValue));
    }

    /**
     * String to long
     * @param longValue
     * @return
     */
    public static long parseLong(String longValue) {
        return Long.parseLong(norNumber(longValue));
    }

}
