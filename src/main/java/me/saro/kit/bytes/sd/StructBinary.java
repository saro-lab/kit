package me.saro.kit.bytes.sd;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  binary data
 * @author      PARK Yong Seo
 * @since       1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StructBinary {
    /**
     * offset
     * @return
     */
    int offset() default -1;

    /**
     * array length
     * use in array data
     * @return
     */
    int arrayLength() default -1;
}
