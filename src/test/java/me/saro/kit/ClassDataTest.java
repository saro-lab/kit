package me.saro.kit;

import org.junit.jupiter.api.Test;

public class ClassDataTest {

    @Test
    public void testLambdas() throws Exception {

        var a = new Abc().bindClass("asdf      bb        ");
        System.out.println(a.c);
        System.out.println(a.b);
        System.out.println(new String(a.toBytes()));
    }

    public static class Abc extends ClassData<Abc> {

        String c;
        String b;

        @Override
        protected Abc toClass(ByteData data) {
            this.c = data.readString(10);
            this.b = data.readString(10);
            return this;
        }

        @Override
        protected void bindByteData(ByteData data) {
            data.writeString(c, 10);
            data.writeString(b, 10);
        }

        @Override
        protected int limit() {
            return 20;
        }
    }


}
