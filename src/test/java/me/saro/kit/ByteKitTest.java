package me.saro.kit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteKitTest {
    
    @Test
    public void base64() {
        String sample = "가나다abcd";
        String encode = ByteKit.encodeBase64String(sample, "UTF-8");
        String decode = ByteKit.decodeBase64(encode, "UTF-8");
        assertEquals(sample, decode);
    }
    
    @Test
    public void toBytesByHex() {
        String sample = "fff0fa32713281af";
        assertEquals(ByteKit.toHex(ByteKit.toBytesByHex(sample)), sample);
        assertEquals(ByteKit.toHex(ByteKit.toBytesByHex("FFff")), "ffff");
        assertEquals(ByteKit.toHex(ByteKit.toBytesByHex("ffFF")), "ffff");
    }
    
    @Test
    public void shortTest() {
        assertEquals(ByteKit.toHex(ByteKit.toBytes((short)32)), "0020");
        assertEquals(ByteKit.toHex(ByteKit.toBytes((short)Short.parseShort("-1"))), "ffff");
        assertEquals(ByteKit.toShort(ByteKit.toBytes((short)123)), (short)123);
    }
    
    @Test
    public void intTest() {
        assertEquals(ByteKit.toHex(ByteKit.toBytes((int)32)), "00000020");
        assertEquals(ByteKit.toHex(ByteKit.toBytes((int)-1)), "ffffffff");
        assertEquals(ByteKit.toInt(ByteKit.toBytes(8080)), 8080);
    }
    
    @Test
    public void longTest() {
        assertEquals(ByteKit.toHex(ByteKit.toBytes((long)32)), "0000000000000020");
        assertEquals(ByteKit.toHex(ByteKit.toBytes(-1L)), "ffffffffffffffff");
        assertEquals(ByteKit.toLong(ByteKit.toBytes(324L)), 324L);
    }
    
    @Test
    public void floatTest() {
        float value = 32;
        byte[] bytes = ByteKit.toBytes(value);
        float rocv = ByteKit.toFloat(bytes);
        assertEquals(value, rocv);
    }
    
    @Test
    public void dobuleTest() {
        double value = 32;
        byte[] bytes = ByteKit.toBytes(value);
        double rocv = ByteKit.toDouble(bytes);
        assertEquals(value, rocv);
    }
}
