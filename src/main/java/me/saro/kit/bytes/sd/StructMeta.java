package me.saro.kit.bytes.sd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Struct
 * @author PARK Yong Seo
 * @since       1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StructMeta {

    /** split token */
    char token();

    /** field count */
    int count();

    /** trim */
    boolean trim() default false;

    /** bind empty is null or zero */
    boolean emptyIsNullOrZero() default false;
}
