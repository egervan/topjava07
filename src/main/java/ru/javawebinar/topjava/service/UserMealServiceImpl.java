package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealServiceImpl implements UserMealService {

    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal) {
        return repository.save(userMeal);
    }

    @Override
    public boolean delete(int userId, int id) {
        if(LoggedUser.id() != userId) throw new NotFoundException("This food doesn't belong to this user");
        boolean result = repository.delete(userId, id);
        if(!result) throw new NotFoundException("This food is not exist!");
        return result;
    }

    @Override
    public UserMealWithExceed get(int userId, int id, int caloriesPerDay) {

        return (UserMealWithExceed) getAllWithExceed(userId, caloriesPerDay).get(id);
    }

    @Override
    public List getAllWithExceed(int userId, int caloriesPerDay) {
        return UserMealsUtil.getWithExceeded(repository.getAll(userId), caloriesPerDay);
    }
}
