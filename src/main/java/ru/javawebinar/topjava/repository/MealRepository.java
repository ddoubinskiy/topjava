package ru.javawebinar.topjava.repository;

import org.springframework.cglib.core.Local;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(int userId, Meal meal);

    // false if not found
    boolean delete(int userId, int id);

    // null if not found
    Meal get(int userId, int id);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}
