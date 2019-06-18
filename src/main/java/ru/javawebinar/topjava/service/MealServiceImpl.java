package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public Meal create(int userId, Meal meal) throws NotFoundException
    {
        return ValidationUtil.checkNotFoundWithId(repository.save(userId, meal), userId);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException
    {
        ValidationUtil.checkNotFoundWithId(repository.delete(userId, id), userId);
    }

    @Override
    public Meal get(int userId, int id) throws NotFoundException
    {
        return ValidationUtil.checkNotFoundWithId(repository.get(userId, id), userId);
    }

    @Override
    public void update(int userId, Meal meal) throws NotFoundException
    {
        ValidationUtil.checkNotFoundWithId(repository.save(userId, meal), userId);
    }

    @Override
    public Collection<Meal> getAll(int userId) throws NotFoundException
    {
        return ValidationUtil.checkNotFoundWithId(repository.getAll(userId), userId);
    }

    @Override
    public Collection<Meal> getAllBetween(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime)
    {
        return ValidationUtil.checkNotFoundWithId(repository.getAllBetween(userId, startDate, endDate, startTime, endTime), userId);
    }
}