package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.toArray;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealTestData
{
    public static final Meal MEAL1 = new Meal(AbstractBaseEntity.START_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(AbstractBaseEntity.START_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(AbstractBaseEntity.START_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(AbstractBaseEntity.START_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL5 = new Meal(AbstractBaseEntity.START_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL6 = new Meal(AbstractBaseEntity.START_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static void assertMatch(Meal actual, Meal expected)
    {
        Assertions.assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "dateTime", "calories", "description");
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected)
    {
        Assertions.assertThat(actual).usingElementComparatorOnFields("id", "dateTime", "calories", "description").isEqualTo(expected);
    }

    public static List<Meal> getDataList()
    {
        return new ArrayList<Meal>()
        {{
            add(MEAL1);
            add(MEAL2);
            add(MEAL3);
            add(MEAL4);
            add(MEAL5);
            add(MEAL6);
        }}.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
    }

}
