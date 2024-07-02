package annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
// @Target(ElementType.PARAMETER)
public @interface Argument {
    String name() default "";
}
