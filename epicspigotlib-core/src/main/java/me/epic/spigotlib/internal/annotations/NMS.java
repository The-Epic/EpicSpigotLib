package me.epic.spigotlib.internal.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that the annotated method uses NMS and will only work in a supported Minecraft version
 */
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface NMS {
    /**
     * The minimum version of Minecraft that this feature is supported on
     */
    String value() default "";
}