package me.saro.kit.bytes.sd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SplitMeta {
    /** split token */
    String token();
    /** field count */
    int count();
    /** charset */
    String charset() default "UTF-8";
}
