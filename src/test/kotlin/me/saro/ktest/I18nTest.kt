package me.saro.ktest

import me.saro.kit.service.I18n.Companion.load
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@DisplayName("I18n Test")
class I18nTest {
    @Test
    fun test1() {
        val dir = File(I18nTest::class.java.classLoader.getResource("lang")?.file ?: "")
        val i18n = load(dir)

        Assertions.assertEquals(i18n["en", "success"], "Success")
        Assertions.assertEquals(i18n["ko", "success"], "성공")
        Assertions.assertEquals(i18n["ko", "success.abc"], "success.abc")
        Assertions.assertEquals(i18n["du", "success"], "Success")

        Assertions.assertEquals(i18n["du;ko", "exception"], "예외")
    }
}
