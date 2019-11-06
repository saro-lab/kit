package me.saro.kit;

import me.saro.commons.__old.bytes.*;
import me.saro.commons.__old.bytes.crypt.Crypt;
import me.saro.commons.__old.bytes.fd.FixedData;
import me.saro.commons.__old.bytes.ftp.FTP;
import me.saro.commons.__old.bytes.json.JsonReader;
import me.saro.commons.__old.bytes.shell.Shell;
import me.saro.commons.__old.bytes.ssh.SSHExecutor;
import me.saro.commons.__old.bytes.ssh.SSHShell;
import me.saro.commons.__old.bytes.web.Web;
import me.saro.kit.bytes.ByteData;
import me.saro.kit.bytes.Bytes;
import me.saro.kit.crypts.Crypt;
import me.saro.kit.dates.DateFormat;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadmeTest {

    @Test
    public void justPrintReadme() {
        
        System.out.println("# STATIC");
        System.out.println();

        outClassInfo(Converter.class);
        outClassInfo(Bytes.class);
        outClassInfo(Utils.class);
        outClassInfo(Files.class);
        outClassInfo(Maps.class);
        outClassInfo(Zips.class);
        outClassInfo(Valids.class);
        outClassInfo(Naming.class);
        outClassInfo(Shell.class);
        
        System.out.println("# INSTANCE");
        System.out.println();

        outClassInfo(FixedData.class);
        outClassInfo(FTP.class);
        outClassInfo(Crypt.class);
        outClassInfo(SSHShell.class);
        outClassInfo(SSHExecutor.class);
        outClassInfo(DateFormat.class);
        outClassInfo(JsonReader.class);
        outClassInfo(ByteData.class);
        outClassInfo(Web.class);
        outClassInfo(NullOutputStream.class);

        System.out.println("# FUNCTION INTERFACE");
        System.out.println("#### me.saro.commons.function.*");
        System.out.println("```\n" + 
                "ThrowableRunnable\n" + 
                "ThrowableSupplier<R>\n" + 
                "ThrowablePredicate<T>\n" + 
                "ThrowableConsumer<T>\n" + 
                "ThrowableBiConsumer<T, U>\n" + 
                "ThrowableTriConsumer<T, U, V>\n" + 
                "ThrowableFunction<T, R>\n" + 
                "ThrowableBiFunction<T, U, R>\n" + 
                "ThrowableTriFunction<T, U, V, R>\n" + 
                "```");
    }

    public void outClassInfo(Class<?> clazz) {

        String className = clazz.getSimpleName();

        System.out.println();
        System.out.println("## " + className);
        System.out.println();
        System.out.println("#### " + clazz.getName());
        System.out.println();

        System.out.println("```");
        Stream.of(clazz.getDeclaredMethods()).map(method ->
            Stream
                .of(getName(className, method) + getParameters(method), getGenericReturnType(method))
                .collect(Collectors.joining(" "))
        ).filter(e -> !e.startsWith("0"))
        .sorted()
        .map(e -> e.substring(1))
        .forEach(System.out::println);
        System.out.println("```");
        System.out.println();
    }

    public String getGenericReturnType(Method method) {
        String rv = method.getGenericReturnType().getTypeName().replaceAll("[a-z]+\\.", "");
        return rv.equals("void") ? "" : ": " +rv; 
    }

    public String getName(String className, Method method) {
        String prefix = Modifier.toString(method.getModifiers());
        prefix = (prefix.startsWith("public static") ? ("1") : (prefix.startsWith("public") ? "2" : "0"));
        return prefix + (prefix.equals("1") ? (className + ".") : "") + method.getName();
    }

    public String getParameters(Method method) {
        // need -parameters options
        return "(" + Stream.of(method.getParameters())
        .map(e -> e.getParameterizedType().getTypeName().replaceAll("[a-z]+\\.", ""))
        .collect(Collectors.joining(", ")) + ")";

    }
}
