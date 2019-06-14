package ru.javawebinar.topjava.dao.local;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealDAOLocalImpl implements MealDAO
{
    private Map<Long, Meal> localStorage = new ConcurrentHashMap<>();

    private AtomicLong currentId = new AtomicLong(-1);

    public MealDAOLocalImpl()
    {
        List<Meal> hardCodedMeals = MealsUtil.getHardCodedMeals();
        for (Meal meal : hardCodedMeals)
        {
            add(meal);
        }
    }

    @Override
    public void add(Meal meal)
    {
        if (meal != null)
        {
            meal.setId(generateNextId());
            localStorage.put(meal.getId(), meal);
        }
    }

    @Override
    public Meal get(long id)
    {
        if (localStorage.containsKey(id))
        {
            return localStorage.get(id);
        }
        return null;
    }

    @Override
    public void update(long id, Meal meal)
    {
        if (localStorage.containsKey(id))
        {
            meal.setId(id);
            localStorage.replace(id, meal);
        }
    }

    @Override
    public void delete(long id)
    {
        if (localStorage.containsKey(id))
        {
            localStorage.remove(id);
        }
    }

    @Override
    public List<Meal> getAll()
    {
        return new ArrayList<>(localStorage.values());
    }

    private long generateNextId()
    {
        return currentId.incrementAndGet();
    }

}
