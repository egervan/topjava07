package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;

import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal save(UserMeal userMeal);
    boolean delete(int userId, int id);
    UserMealWithExceed get(int userId, int id, int caloriesPerDay);
    List getAllWithExceed(int userId, int caloriesPerDay);


}
