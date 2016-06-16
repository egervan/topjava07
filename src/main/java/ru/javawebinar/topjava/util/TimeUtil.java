package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalDateTime lt, LocalDateTime startTime, LocalDateTime endTime) {
        return lt.toLocalTime().compareTo(startTime.toLocalTime()) >= 0 && lt.toLocalTime().compareTo(endTime.toLocalTime()) <= 0
               && lt.toLocalDate().compareTo(startTime.toLocalDate()) >= 0 && lt.toLocalDate().compareTo(endTime.toLocalDate()) <= 0;
    }

    /* default method
    public static boolean isBetween(LocalDateTime lt, LocalDateTime startTime, LocalDateTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0 ;
    }*/

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }
}
