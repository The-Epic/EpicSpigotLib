package me.epic.spigotlib.utils;

/**
 * The TickUtils class provides utility methods for converting time units to server ticks.
 */
public class TickUtils {

    /**
     * Converts the given time to server ticks.
     *
     * @param hours the number of hours
     * @param minutes the number of minutes
     * @param seconds the number of seconds
     * @return the number of server ticks
     */
    public static long fromAll(double hours, double minutes, double seconds) {
        return fromHours(hours) + fromMinutes(minutes) + fromSeconds(seconds);
    }

    /**
     * Converts the given time to server ticks.
     *
     * @param hours the number of hours
     * @param minutes the number of minutes
     * @return the number of server ticks
     */
    public static long fromHoursMinutes(double hours, double minutes) {
        return fromAll(hours, minutes, 0);
    }

    /**
     * Converts the given time to server ticks.
     *
     * @param hours the number of hours
     * @return the number of server ticks
     */
    public static long fromHours(double hours) {
        return fromMinutes(hours * 60);
    }

    /**
     * Converts the given time to server ticks.
     *
     * @param minutes the number of minutes
     * @return the number of server ticks
     */
    public static long fromMinutes(double minutes) {
        return fromSeconds(minutes * 60);
    }

    /**
     * Converts the given time to server ticks.
     *
     * @param seconds the number of seconds
     * @return the number of server ticks
     */
    public static long fromSeconds(double seconds) {
        return (long) (seconds * 20);
    }
}


