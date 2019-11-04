package me.saro.kit;

import me.saro.commons.__old.bytes.shell.Shell;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ShellTest {

    @Test
    public void test() throws Exception {
          //example();
    }

    public void example() throws IOException {
        // windows cmd dir
        System.out.println(Shell.execute("cmd", "/c", "dir"));
    }
}

