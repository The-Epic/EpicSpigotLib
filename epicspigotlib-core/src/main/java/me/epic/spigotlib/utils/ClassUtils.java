package me.epic.spigotlib.utils;

import lombok.SneakyThrows;

/**
 * A utility class for working with Java classes.
 */
public class ClassUtils {

    /**
     * Returns the class of the caller at a given offset in the call stack.
     *
     * @param offset the number of stack frames to skip before returning the class.
     * @return the class of the caller at the given offset in the call stack.
     * @throws ClassNotFoundException if the class could not be found.
     */
    @SneakyThrows
    public static Class<?> getCurrentClass(final int offset) {
        return Class.forName(Thread.currentThread().getStackTrace()[2 + offset].getClassName());
    }
}