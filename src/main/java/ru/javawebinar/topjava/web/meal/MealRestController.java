package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController
{
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    @Autowired
    public MealRestController(MealService service)
    {
        this.service = service;
    }

    public Collection<MealTo> getAll()
    {
        return MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id)
    {
        return service.get(SecurityUtil.authUserId(), id);
    }

    public Meal create(Meal meal)
    {
        log.info("create meal {}", meal);
        checkNew(meal);
        return service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id)
    {
        log.info("delete meal {}", id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal, int id)
    {
        log.info("update meal {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }

    public Collection<MealTo> getAllFiltered(String startDate, String endDate, String startTime, String endTime)
    {
        LocalDate startLd = LocalDate.MIN;
        LocalDate endLd = LocalDate.MAX;
        if (startDate != null && !startDate.isEmpty()) startLd = LocalDate.parse(startDate);
        if (endDate != null && !endDate.isEmpty()) endLd = LocalDate.parse(endDate);

        Collection<Meal> filteredByDate = service.getAllBetween(SecurityUtil.authUserId(), startLd, endLd);
        LocalTime startLt = LocalTime.MIN;
        LocalTime endLt = LocalTime.MAX;
        if (startTime != null && !startTime.isEmpty()) startLt = LocalTime.parse(startTime);
        if (endTime != null && !endTime.isEmpty()) endLt = LocalTime.parse(endTime);
        return MealsUtil.getFilteredWithExcess(filteredByDate, SecurityUtil.authUserCaloriesPerDay(), startLt, endLt);
    }
}