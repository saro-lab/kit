package me.saro.test.lang;

import me.saro.kit.lang.KoreanKit;
import org.junit.jupiter.api.Test;

public class KoreanKitTest {

    @Test
    public void test() throws Exception {
        assert KoreanKit.toChosung("SARO KIT은 라이브러리다.").equals("SARO KITㅇ ㄹㅇㅂㄹㄹㄷ.");

        assert KoreanKit.toJaso(null).equals("");
        assert KoreanKit.toJasoAtom(null).equals("");
        assert KoreanKit.toJaso("").equals("");
        assert KoreanKit.toJasoAtom("").equals("");

        assert KoreanKit.toJaso("많다 aB1.").equals("ㅁㅏㄶㄷㅏ aB1.");
        assert KoreanKit.toJasoAtom("많다 aB1.").equals("ㅁㅏㄴㅎㄷㅏ aB1.");
    }
}
