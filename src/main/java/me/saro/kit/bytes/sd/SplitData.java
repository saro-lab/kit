package me.saro.kit.bytes.sd;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SplitData {

    private synchronized static <R> Splitter getSplitter(R instance) {
        Class<?> clazz = instance.getClass();
        Splitter splitter = Splitter.store.get(clazz.getName());
        if (splitter == null) {
            Splitter.store.put(clazz.getName(), (splitter = new Splitter(clazz)));
        }
        return splitter;
    }

//    public static <R> R bind(R instance) {
//
//
//
//        return instance;
//    }
//
//    public static <R> byte[] toBytes(R data) {
//
//    }
//
//    public static <R> String toString(R data) {
//
//    }
//


    public static void main(String[] args) {
        Splitter s = getSplitter(new Aaa());
    }

    @SplitMeta(token = "|", count = 3)
    public static class Aaa {
        @SplitIndex(1)
        public String aa = "1";
        @SplitIndex(2)
        public int bb = 32;
        @SplitIndex(0)
        public long cc = -123L;


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



    private static class Splitter {
        private static Map<String, Splitter> store = new HashMap<>();
        private Class<?> clazz;
        private SplitMeta meta;

        private Charset charset;


        public Splitter(Class<?> clazz) {
            this.clazz = clazz;
            init();
        }

        private void init() {
            meta = clazz.getDeclaredAnnotation(SplitMeta.class);
            if (meta == null) {
                throw new RuntimeException(clazz.getName() + " does not defined @SplitMeta");
            }
            if (meta.token().isEmpty()) {
                throw new RuntimeException(clazz.getName() + " token in the @SplitMeta is empty");
            }
            if (meta.count() < 1) {
                throw new RuntimeException(clazz.getName() + " minimum count in the @SplitMeta is 1");
            }
            charset = Charset.forName(meta.charset());

            Map<String, Method> methods = Arrays.asList(clazz.getDeclaredMethods()).parallelStream()
                    .filter(e -> (e.getName().startsWith("get")) || e.getName().startsWith("set"))
                    .collect( Collectors.toMap(Method::getName, Function.identity()));

            System.out.println(methods);

            Arrays.asList(clazz.getDeclaredFields()).parallelStream().forEach(field -> {
                SplitIndex index = field.getDeclaredAnnotation(SplitIndex.class);
                if (index != null) {
                    String name = field.getName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method get = methods.get("get" + name);
                    Method set = methods.get("set" + name);
                    if (get == null) {
                        throw new RuntimeException(clazz.getName() + "." + field.getName() + " need to getter");
                    } else if (get.getParameterCount() != 0) {
                        throw new RuntimeException(clazz.getName() + "." + field.getName() + "' is invalid");
                    } else {

                    }
                    if (set == null) {
                        throw new RuntimeException(clazz.getName() + "." + field.getName() + " need to setter");
                    } else if (get.getParameterCount() != 1) {
                        throw new RuntimeException(clazz.getName() + "." + field.getName() + "' is invalid");
                    } else {

                    }

                    System.out.println(get);
                    System.out.println(set);
                }
            });
        }
    }
}
