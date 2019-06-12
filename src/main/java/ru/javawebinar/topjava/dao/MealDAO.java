package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO
{
    void add(Meal meal);

    Meal get(long id);

    void update(long id, Meal meal);

    void delete(long id);

    List<Meal> getAll();
}
