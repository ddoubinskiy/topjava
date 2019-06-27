package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository()
public class JdbcMealRepositoryImpl implements MealRepository
{

    private static final BeanPropertyRowMapper<Meal> MAPPER = new BeanPropertyRowMapper<>(Meal.class);

    private final SimpleJdbcInsert insert;

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbcTemplate = namedJdbcTemplate;
        insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId)
    {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("datetime", Timestamp.valueOf(meal.getDateTime()))
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew())
        {
            Number newId = insert.executeAndReturnKey(parameterSource);
            meal.setId(newId.intValue());
            return meal;
        } else
        {
            return namedJdbcTemplate.update("UPDATE meals SET datetime=:datetime, description=:description, calories=:calories " +
                    "WHERE id=:id AND user_id=:user_id", parameterSource) == 0 ? null : meal;
        }
    }

    @Override
    public boolean delete(int id, int userId)
    {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId)
    {
        Meal meal;
        try
        {
            meal = jdbcTemplate.queryForObject("SELECT * FROM meals WHERE id=? AND user_id=?", MAPPER, id, userId);
        } catch (DataAccessException ex)
        {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId)
    {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC", MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId)
    {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("startDate", Timestamp.valueOf(startDate))
                .addValue("endDate", Timestamp.valueOf(endDate));

        return namedJdbcTemplate.query("SELECT * FROM meals WHERE datetime BETWEEN :startDate AND :endDate ORDER BY datetime DESC", parameterSource, MAPPER);
    }
}
