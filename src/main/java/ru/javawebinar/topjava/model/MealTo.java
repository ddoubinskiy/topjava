package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {
    private final long id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealTo(long id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    @Override
    public String toString()
    {
        final StringBuffer sb = new StringBuffer("MealTo{");
        sb.append("id=").append(id);
        sb.append(", dateTime=").append(dateTime);
        sb.append(", description='").append(description).append('\'');
        sb.append(", calories=").append(calories);
        sb.append(", excess=").append(excess);
        sb.append('}');
        return sb.toString();
    }

    public long getId()
    {
        return id;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public String getDescription()
    {
        return description;
    }

    public int getCalories()
    {
        return calories;
    }

    public boolean isExcess()
    {
        return excess;
    }
}