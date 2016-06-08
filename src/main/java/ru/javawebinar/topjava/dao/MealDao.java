package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Map;

/**
 * Created by Jager on 08.06.2016.
 */
public interface MealDao {
    void createMeal(UserMeal userMeal);
    UserMeal readMeal(int id);
    void updateMeal(UserMealWithExceed userMealWithExceed);
    void delete(int id);
    Map<Integer, UserMeal> getAllMeals();
    Map<Integer, UserMealWithExceed> getMealWithExceed(int caloriesPerDay);
}
