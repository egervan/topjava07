package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal create(UserMeal userMeal);
    void update(UserMeal userMeal);
    void delete(int userId, int id);
    UserMeal get(int userId, int id);
//    UserMealWithExceed get(int userId, int id, int caloriesPerDay);
    List<UserMealWithExceed> getBetweenDateTime(int userId, int caloriesPerDay, LocalDateTime start, LocalDateTime end);
    List<UserMealWithExceed> getAll(int userId, int caloriesPerDay);

}
