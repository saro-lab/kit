package me.saro.kit;

import me.saro.kit.bytes.sd.SplitData;
import me.saro.kit.bytes.sd.SplitIndex;
import me.saro.kit.bytes.sd.SplitMeta;
import org.junit.jupiter.api.Test;

public class SplitDataTest {

    @Test
    public void testLambdas() throws Exception {

        System.out.println(SplitData.toString(new Abc()));

    }

    @SplitMeta(token = ',', count = 3)
    public static class Abc {
        @SplitIndex(0)
        String aa = "가나다라";
        @SplitIndex(1)
        int bb = -324;
        @SplitIndex(2)
        long cc = 34111L;

        public String getAa() {
            return aa;
        }

        public void setAa(String aa) {
            this.aa = aa;
        }

        public int getBb() {
            return bb;
        }

        public void setBb(int bb) {
            this.bb = bb;
        }

        public long getCc() {
            return cc;
        }

        public void setCc(long cc) {
            this.cc = cc;
        }
    }
}
