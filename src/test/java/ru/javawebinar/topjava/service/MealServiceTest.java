package ru.javawebinar.topjava.service;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring-test.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("jdbc")
public class MealServiceTest
{
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception
    {
       assertMatch(service.get(MEAL3.getId(), USER_ID), MEAL3);
    }

    @Test
    public void delete() throws Exception
    {
        service.delete(MEAL2.getId(), USER_ID);
        List<Meal> dataList = getDataList();
        dataList.remove(MEAL2);
        assertMatch(service.getAll(USER_ID), dataList);
    }

    @Test
    public void getBetweenDates() throws Exception
    {
        LocalDate startDate = LocalDate.of(2015, Month.MAY, 31);
        LocalDate endDate = LocalDate.of(2015, Month.JUNE, 1);
        List<Meal> betweenDates = service.getBetweenDates(startDate, endDate, USER_ID);
        List<Meal> filteredTestDataList = getDataList().stream().filter(meal -> Util.isBetween(meal.getDate(), startDate, endDate)).collect(Collectors.toList());
        assertMatch(betweenDates, filteredTestDataList);
    }

    @Test
    public void getBetweenDateTimes() throws Exception
    {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.MAY, 30, 17, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.MAY, 31, 15, 0);

        List<Meal> betweenDates = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        List<Meal> filteredTestDataList = getDataList().stream().filter(meal -> Util.isBetween(meal.getDateTime(), startDateTime, endDateTime)).collect(Collectors.toList());
        assertMatch(betweenDates, filteredTestDataList);
    }

    @Test
    public void getAll() throws Exception
    {
        assertMatch(service.getAll(USER_ID), getDataList());
    }

    @Test
    public void update() throws Exception
    {
        Meal newMeal = new Meal(service.get(MEAL4.getId(), USER_ID));
        newMeal.setCalories(900);
        newMeal.setDescription("Changed meal");
        service.update(newMeal, USER_ID);
        assertMatch(service.get(MEAL4.getId(), USER_ID), newMeal);
    }

    @Test
    public void create() throws Exception
    {
        Meal newMeal = new Meal(LocalDateTime.now(), "Lunch", 1000);
        Meal meal = service.create(newMeal, USER_ID);
        newMeal.setId(meal.getId());
        assertMatch(meal, newMeal);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserGet() throws Exception
    {
        service.get(MEAL5.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserDelete() throws Exception
    {
        service.delete(MEAL5.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void wrongUserUpdate() throws Exception
    {
        Meal newMeal = new Meal(service.get(MEAL6.getId(), USER_ID));
        newMeal.setCalories(900);
        newMeal.setDescription("Changed meal");
        service.update(newMeal, ADMIN_ID);
    }
}