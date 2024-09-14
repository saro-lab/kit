package me.saro.ktest

import me.saro.kit.service.I18n.Companion.load
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File
import java.util.Locale

@DisplayName("I18n Test")
class I18nTest {
    @Test
    fun test1() {
        val dir = File(I18nTest::class.java.classLoader.getResource("lang")?.file ?: "")
        val i18n = load(dir)

        Assertions.assertEquals(i18n["success", "en"], "Success")
        Assertions.assertEquals(i18n["success", "ko"], "성공")
        Assertions.assertEquals(i18n["success.abc", "ko"], "success.abc")
        Assertions.assertEquals(i18n["success.abc", ""], "success.abc")
        Assertions.assertEquals(i18n["success", "du"], "Success")

        Assertions.assertEquals(i18n["exception", "du;ko"], "예외")

        Assertions.assertEquals(i18n["success", "du"], "Success")

        Assertions.assertEquals(i18n["success", listOf<String>()], "Success")
        Assertions.assertEquals(i18n.byLocales("success", listOf<Locale>()), "Success")
        Assertions.assertEquals(i18n["success", listOf<String>("_3")], "Success")
        Assertions.assertEquals(i18n["success", listOf<String>("ko")], "성공")
        Assertions.assertEquals(i18n.byLocales("success", listOf<Locale>(Locale.KOREAN)), "성공")
        Assertions.assertEquals(i18n.byLocale("success", Locale.KOREAN), "성공")
    }
}
