package ru.pantherapi.scanner.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IndexedApiContract {
    String value() default "";

    String version() default "";

    ApiContractInteractionType interactionType();

    boolean onlyExplicitlyIncluded() default false;

    String description() default "";
}
