package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {
    Meal create(int userId, Meal meal) throws NotFoundException;

    void delete(int userId, int id) throws NotFoundException;

    Meal get(int userId, int id) throws NotFoundException;

    void update(int userId, Meal meal) throws NotFoundException;

    Collection<Meal> getAll(int userId) throws NotFoundException;

    Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
}