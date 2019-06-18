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
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    private int authUserId = SecurityUtil.authUserId();

    @Autowired
    public MealRestController(MealService service)
    {
        this.service = service;
    }

    public Collection<MealTo> getAll() {
        return MealsUtil.getWithExcess(service.getAll(authUserId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        return service.get(authUserId, id);
    }

    public Meal create(Meal meal) {
        log.info("create meal {}", meal);
        checkNew(meal);
        return service.create(authUserId, meal);
    }

    public void delete(int id) {
        log.info("delete meal {}", id);
        service.delete(authUserId, id);
    }

    public void update(Meal meal, int id) {
        log.info("update meal {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(authUserId, meal);
    }

    public Collection<MealTo> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime)
    {
        return Collections.emptyList();
    }
}