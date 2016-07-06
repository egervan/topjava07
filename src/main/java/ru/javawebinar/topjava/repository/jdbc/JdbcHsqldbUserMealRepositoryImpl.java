package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealRowMapper;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Profile("hsqldb")
public class JdbcHsqldbUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> ROW_MAPPER = new UserMealRowMapper();
    private static final RowMapper<User> USER_ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUserMeal;

    @Autowired
    public JdbcHsqldbUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUserMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories())
                .addValue("date_time", TimeUtil.toTimestampConverter(userMeal.getDateTime()))
                .addValue("user_id", userId);

        if (userMeal.isNew()) {
            Number newId = insertUserMeal.executeAndReturnKey(map);
            userMeal.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE meals " +
                            "   SET description=:description, calories=:calories, date_time=:date_time " +
                            " WHERE id=:id AND user_id=:user_id"
                    , map) == 0) {
                return null;
            }
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }


    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> userMeals = jdbcTemplate.query(
                "SELECT * FROM meals WHERE meals.id = ? AND user_id = ?", ROW_MAPPER, id, userId);//LEFT JOIN users ON meals.user_id = users.id
        List<User> usersList = jdbcTemplate.query(
                "SELECT * FROM users WHERE users.id = ?", USER_ROW_MAPPER, userId);
        User user = DataAccessUtils.singleResult(usersList);
        UserMeal meal = DataAccessUtils.singleResult(userMeals);
        if(meal == null) return null;
        meal.setUser(user);
        return meal;
    }

    public List<UserMeal> getAll(int userId) {
        List<UserMeal> meals = jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
        List<User> usersList = jdbcTemplate.query(
                "SELECT * FROM users WHERE users.id = ?", USER_ROW_MAPPER, userId);
        User currentUser = DataAccessUtils.singleResult(usersList);
        meals.stream().forEach(meal -> meal.setUser(currentUser));
        return meals;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Timestamp start = TimeUtil.toTimestampConverter(startDate);
        Timestamp end = TimeUtil.toTimestampConverter(endDate);
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time >=  ? AND date_time <= ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, start, end);
    }
}