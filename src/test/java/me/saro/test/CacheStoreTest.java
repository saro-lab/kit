package me.saro.test;

import me.saro.kit.CacheStore;
import org.junit.jupiter.api.Test;

public class CacheStoreTest {

    @Test
    public void test() throws Exception {
        CacheStore<String, String> cs = new CacheStore<>(100);

        assert cs.get("aa", (id) -> "bb").equals("bb");

        assert cs.get("aa", (id) -> "cc").equals("bb");

        assert cs.get("dd", (id) -> "cc").equals("cc");
    }
}
