package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class DateTimeUtil
{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime)
    {
        return isBetweenGeneric(lt, startTime, endTime);
    }

    public static <T extends Comparable<? super T>> boolean isBetweenGeneric(T current, T start, T end)
    {
        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0;
    }

    public static boolean isBetween(LocalDate ld, LocalDate startDate, LocalDate endDate)
    {
        return isBetweenGeneric(ld, startDate, endDate);
    }

    public static String toString(LocalDateTime ldt)
    {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
