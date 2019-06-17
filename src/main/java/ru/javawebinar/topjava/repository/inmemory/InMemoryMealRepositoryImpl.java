package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        repository.put(SecurityUtil.authUserId(), new HashMap<>());
        MealsUtil.MEALS.forEach(meal -> save(SecurityUtil.authUserId(), meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save meal {}, user id={}", meal, userId);
        if(repository.containsKey(userId))
        {
            repository.putIfAbsent(userId, new HashMap<>());
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.get(userId).put(meal.getId(), meal);
                return meal;
            }
            // treat case: update, but absent in storage
            return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int id)
    {
        log.info("delete meal id={}, userId={}", id, userId);
        return repository.containsKey(userId) && repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get meal id={}, userId={}", id, userId);
        if(repository.containsKey(userId))
        {
            return repository.get(userId).get(id);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get all meals, userId={}", userId);
        if(repository.containsKey(userId))
        {
            return repository.get(userId).values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


}

