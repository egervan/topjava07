package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.RowMapper;
import ru.javawebinar.topjava.model.UserMeal;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMealRowMapper implements RowMapper<UserMeal> {

    @Override
    public UserMeal mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMeal meal = new UserMeal();
        meal.setId(rs.getInt("id"));
        meal.setCalories(rs.getInt("calories"));
        meal.setDateTime(TimeUtil.toLocalDateTimeConverter(rs.getTimestamp("date_time")));
        meal.setDescription(rs.getString("description"));

        return meal;
    }
}
