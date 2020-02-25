package me.saro.kit.bytes.sd;

import me.saro.kit.Texts;
import me.saro.kit.functions.ThrowableBiConsumer;
import me.saro.kit.functions.ThrowableFunction;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SplitData {

    private synchronized static <R> Splitter getSplitter(R instance) {
        Class<?> clazz = instance.getClass();
        Splitter splitter = Splitter.store.get(clazz.getName());
        if (splitter == null) {
            Splitter.store.put(clazz.getName(), (splitter = new Splitter(clazz)));
        }
        return splitter;
    }

    public static <R> R toClass(R instance, String node) {
        getSplitter(instance).input(instance, node);
        return instance;
    }

    public static <R> R toClass(R instance, List<String> node) {
        getSplitter(instance).input(instance, node);
        return instance;
    }

    public static String toString(Object instance) {
        return getSplitter(instance).output(instance);
    }

    private static class Splitter {
        private static Map<String, Splitter> store = new HashMap<>();

        private Class<?> clazz;
        private SplitMeta meta;

        private ThrowableFunction<Object, String>[] output;
        private ThrowableBiConsumer<Object, String>[] input;

        public Splitter(Class<?> clazz) {
            this.clazz = clazz;
            init();
        }

        public void input(Object object, String data) {
            input(object, Texts.split(data, meta.token()));
        }

        public void input(Object object, List<String> data) {
            if (data.size() != meta.count()) {
                throw new RuntimeException(clazz.getName() + " size is [" + meta.count() + "] but data is [" + data.size() + "]: " + data);
            }
            final boolean trim = meta.trim();
            final boolean emptyIsNullOrZero = meta.emptyIsNullOrZero();
            for (int i = 0 ; i < data.size() ; i++) {
                try {
                    String v = trim ? data.get(i).trim() : data.get(i);
                    v = emptyIsNullOrZero && v.isEmpty() ? null : v;
                    input[i].accept(object, v);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public String output(Object object) {
            return Stream.of(output).<String>map(meta.trim()
                    ? ThrowableFunction.wrap(e -> e.apply(object).trim())
                    : ThrowableFunction.wrap(e -> e.apply(object)))
                    .collect(Collectors.joining(Character.toString(meta.token())));
        }

        private void init() {
            meta = clazz.getDeclaredAnnotation(SplitMeta.class);
            if (meta == null) {
                throw new RuntimeException(clazz.getName() + " does not defined @SplitMeta");
            }
            if (meta.count() < 1) {
                throw new RuntimeException(clazz.getName() + " minimum count in the @SplitMeta is 1");
            }
            output = new ThrowableFunction[meta.count()];
            input = new ThrowableBiConsumer[meta.count()];

            Map<String, Method> methods = Arrays.asList(clazz.getDeclaredMethods()).parallelStream()
                    .filter(e -> (e.getName().startsWith("get")) || e.getName().startsWith("set"))
                    .collect( Collectors.toMap(Method::getName, Function.identity()));

            Arrays.asList(clazz.getDeclaredFields()).parallelStream().forEach(field -> {
                SplitIndex splitIndex = field.getDeclaredAnnotation(SplitIndex.class);
                if (splitIndex != null) {
                    String name = field.getName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);

                    Method get = methods.get("get" + name);
                    Method set = methods.get("set" + name);

                    final int idx = splitIndex.value();

                    if (get == null) {
                        throw new RuntimeException(clazz.getName() + "." + field.getName() + " need to getter");
                    } else if (get.getParameterCount() != 0) {
                        throw new RuntimeException(clazz.getName() + "." + get.getName() + "' is invalid");
                    } else {
                        switch (get.getReturnType().getName()) {
                            case "java.lang.String": output[idx] = (Object data) -> Objects.toString(get.invoke(data), ""); break;
                            case "long": output[idx] = (Object data) -> Long.toString((long)get.invoke(data)); break;
                            case "int": output[idx] = (Object data) -> Integer.toString((int)get.invoke(data)); break;
                            default: throw new RuntimeException(clazz.getName() + "." + get.getName() + "' does not support return type `" + get.getReturnType().getName() + "`");
                        }
                    }

                    if (set == null) {
                        throw new RuntimeException(clazz.getName() + "." + field.getName() + " need to setter");
                    } else if (set.getParameterCount() != 1) {
                        throw new RuntimeException(clazz.getName() + "." + set + "' is invalid");
                    } else {
                        switch (set.getParameterTypes()[0].getName()) {
                            case "java.lang.String": input[idx] = (Object data, String val) -> set.invoke(data, val); break;
                            case "long": input[idx] = (Object data, String val) -> set.invoke(data, val != null ? Long.parseLong(val) : 0L); break;
                            case "int": input[idx] = (Object data, String val) -> set.invoke(data, val != null ? Integer.parseInt(val) : 0); break;
                            default: throw new RuntimeException(clazz.getName() + "." + get.getName() + "' does not support return type `" + get.getParameterTypes()[0].getName() + "`");
                        }
                    }
                }
            });

            for (int i = 0 ; i < output.length ; i++) {
                if (output[i] == null) {
                    throw new RuntimeException(clazz.getName() + " @SplitIndex("+i+") getter does not exist");
                }
            }

            for (int i = 0 ; i < input.length ; i++) {
                if (input[i] == null) {
                    throw new RuntimeException(clazz.getName() + " @SplitIndex("+i+") setter does not exist");
                }
            }
        }
    }
}
