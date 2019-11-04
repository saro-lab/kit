package me.saro.kit;

import me.saro.commons.__old.bytes.Utils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilsTest {

    @Test
    public void executeAllThreads() throws Exception {
        
        List<String> seqs = IntStream.range(1, 11).mapToObj(e -> Integer.toString(e)).collect(Collectors.toList());
        
        List<String> rv = Utils.executeAllThreads(10, seqs, e -> e + "P");
        
        assertEquals("10P1P2P3P4P5P6P7P8P9P", rv.stream().sorted().collect(Collectors.joining("")));
    }
    
    @Test
    public void norNumber() {
        assertEquals(Utils.norNumber("1,000"), "1000");
        assertEquals(Utils.norNumber("-000123"), "-123");
        assertEquals(Utils.norNumber("+0001,234"), "1234");
        assertEquals(Utils.norNumber("000123"), "123");
        assertEquals(Utils.norNumber("000123.0000"), "123");
        assertEquals(Utils.norNumber("123.0"), "123");
        assertEquals(Utils.norNumber(" 123,456"), "123456");
    }

    @Test
    public void zerofill() {
        assertEquals(Utils.zerofill(31, 4), "0031");
        assertEquals(Utils.zerofill(313241982L, 20), "00000000000313241982");
        assertEquals(Utils.zerofill("38", 2), "38");
        assertEquals(Utils.zerofill("318", 4), "0318");
        assertThrows(IllegalArgumentException.class, () -> Utils.zerofill(1, 0));
        assertThrows(IllegalArgumentException.class, () -> Utils.zerofill(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> Utils.zerofill("asdf", 10));
        assertThrows(IllegalArgumentException.class, () -> Utils.zerofill(3124, 2));
    }
}
