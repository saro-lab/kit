package me.saro.kit;

import org.junit.jupiter.api.Test;

public class ClassSerializerTest {

    @Test
    public void testLambdas() throws Exception {

        var a = new Abc().deserialize("asdf      bb        ");
        System.out.println(a.c);
        System.out.println(a.b);
        System.out.println(new String(a.serialize()));
    }

    public static class Abc extends ClassSerializer<Abc> {

        String c;
        String b;

        @Override
        protected void deserialize(ByteData data) {
            this.c = data.readString(10);
            this.b = data.readString(10);
        }

        @Override
        protected void serialize(ByteData data) {
            data.writeString(c, 10);
            data.writeString(b, 10);
        }

        @Override
        protected int limit() {
            return 20;
        }
    }


}
