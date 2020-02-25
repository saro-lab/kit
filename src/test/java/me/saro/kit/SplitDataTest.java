package me.saro.kit;

import me.saro.kit.bytes.sd.SplitData;
import me.saro.kit.bytes.sd.SplitIndex;
import me.saro.kit.bytes.sd.SplitMeta;
import org.junit.jupiter.api.Test;

public class SplitDataTest {

    @Test
    public void testLambdas() {
        assert ",-324,34111".equals(new Abc().toString());
        assert "가나다라,1,2".equals(new Abc("가나다라", 1, 2).toString());
        assert ",0,0".equals(new Abc("", 0, 0).toString());

        assert ",0,0".equals(SplitData.toClass(new Abc(), ",,").toString());
        assert "가나,0,0".equals(SplitData.toClass(new Abc(), "  가나   ,,").toString());
    }

    @SplitMeta(token = ',', count = 3, emptyIsNullOrZero = true, trim = true)
    public static class Abc {
        @SplitIndex(0)
        String aa = null;
        @SplitIndex(1)
        int bb = -324;
        @SplitIndex(2)
        long cc = 34111L;

        public Abc() {
        }

        public Abc(String aa, int bb, long cc) {
            this.aa = aa;
            this.bb = bb;
            this.cc = cc;
        }

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

        @Override
        public String toString() {
            return SplitData.toString(this);
        }
    }
}
