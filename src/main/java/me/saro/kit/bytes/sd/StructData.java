package me.saro.kit.bytes.sd;

import java.util.List;

public class StructData {
    private synchronized static <R> Structure getStructure(R instance) {
        Class<?> clazz = instance.getClass();
        Structure splitter = Structure.store.get(clazz.getName());
        if (splitter == null) {
            Structure.store.put(clazz.getName(), (splitter = new Structure(clazz)));
        }
        return splitter;
    }

    public static <R> R toClass(R instance, String node) {
        getStructure(instance).input(instance, node);
        return instance;
    }

    public static <R> R toClass(R instance, List<String> node) {
        getStructure(instance).input(instance, node);
        return instance;
    }

    public static String toString(Object instance) {
        return getStructure(instance).output(instance);
    }

    private static class Structure {

    }

    private static class DataMeta {
        String type = "";
        int offset = -1;
        int length = -1;
        byte fill = ' ';

    }

    private static class DataType {

    }
}
