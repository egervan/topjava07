package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal create(UserMeal userMeal) {
        return repository.save(userMeal);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException{
        ExceptionUtil.checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public UserMeal get(int userId, int id) throws NotFoundException{
        return  repository.get(userId, id);
        //      return  ExceptionUtil.checkNotFoundWithId(getBetweenDateTime(userId, caloriesPerDay, LocalDateTime.MIN, LocalDateTime.MAX).get(id), id);
    }

  /*  @Override
    public UserMealWithExceed get(int userId, int id, int caloriesPerDay) throws NotFoundException{
        return  repository.get(userId, id);
  //      return  ExceptionUtil.checkNotFoundWithId(getBetweenDateTime(userId, caloriesPerDay, LocalDateTime.MIN, LocalDateTime.MAX).get(id), id);
    }*/

    @Override
    public List<UserMealWithExceed> getBetweenDateTime(int userId, int caloriesPerDay, LocalDateTime start, LocalDateTime end) {
        return UserMealsUtil.getWithExceeded(repository.getBetweenDateTime(userId, start, end), caloriesPerDay);
    }

    @Override
    public List<UserMealWithExceed> getAll(int userId, int caloriesPerDay) {
        return getBetweenDateTime(userId, caloriesPerDay, LocalDateTime.MIN, LocalDateTime.MAX);
    }

    @Override
    public void update(UserMeal userMeal) {
        repository.save(userMeal);
    }

}
