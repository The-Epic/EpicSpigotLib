package me.epic.spigotlib.utils;

public class TickUtils {

    public static long fromAll(double hours, double minutes, double seconds) {
        return fromHours(hours) + fromMinutes(minutes) + fromSeconds(seconds);
    }

    public static long fromHoursMinutes(double hours, double minutes) {
        return fromAll(hours, minutes, 0);
    }

    public static long fromHours(double hours) {
        return fromMinutes(hours * 60);
    }

    public static long fromMinutes(double minutes) {
        return fromSeconds(minutes * 60);
    }

    public static long fromSeconds(double seconds) {
        return (long) (seconds * 20);
    }
}