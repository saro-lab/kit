package me.saro.jtest;

import me.saro.kit.service.I18n;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

@DisplayName("I18n Test")
public class I18nTest {
    @Test
    public void test1() {

        var dir = new File(me.saro.ktest.I18nTest.class.getClassLoader().getResource("lang").getFile());
        var i18n = I18n.load(dir);

        Assertions.assertEquals(i18n.get("en", "success"), "Success");
        Assertions.assertEquals(i18n.get("ko", "success"), "성공");
        Assertions.assertEquals(i18n.get("ko", "success.abc"), "success.abc");
        Assertions.assertEquals(i18n.get("du", "success"), "Success");

        Assertions.assertEquals(i18n.get("du;ko", "exception"), "예외");

    }
}
