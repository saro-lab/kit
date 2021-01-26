package me.saro.test.lang;

import me.saro.kit.lang.Koreans;
import org.junit.jupiter.api.Test;

public class KoreansTest {

    @Test
    public void test() throws Exception {
        assert Koreans.toChosung("SARO KIT은 라이브러리다.").equals("SARO KITㅇ ㄹㅇㅂㄹㄹㄷ.");

        assert Koreans.toJaso(null).equals("");
        assert Koreans.toJasoAtom(null).equals("");
        assert Koreans.toJaso("").equals("");
        assert Koreans.toJasoAtom("").equals("");

        assert Koreans.toJaso("많다 aB1.").equals("ㅁㅏㄶㄷㅏ aB1.");
        assert Koreans.toJasoAtom("많다 aB1.").equals("ㅁㅏㄴㅎㄷㅏ aB1.");
    }
}
