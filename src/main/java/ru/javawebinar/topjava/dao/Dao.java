package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Map;

/**
 * Created by Jager on 08.06.2016.
 */
public interface Dao {
    void create(UserMeal userMeal);
    UserMeal read(int id);
    void update(UserMeal userMeal);
    void delete(int id);
    Map getAllMeals();
    Map getMealWithExceed(int caloriesPerDay);
}
