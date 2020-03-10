package me.saro.kit.webs;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface WebReader {
    void read(int status, Map<String, List<String>> headers, InputStream is) throws Exception;
}
