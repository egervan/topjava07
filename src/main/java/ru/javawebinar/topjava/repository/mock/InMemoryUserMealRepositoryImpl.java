package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        if(!repository.containsKey(userMeal.getUserId())) {
            repository.put(userMeal.getUserId(), new ConcurrentHashMap<>());
        }

        repository.get(userMeal.getUserId()).put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int userId, int id) {
        repository.get(userId).remove(id);
        return true;
    }

    @Override
    public UserMeal get(int userId, int id) {

        return repository.get(userId).get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        List<UserMeal> allMeals = new ArrayList(repository.get(userId).values());
        allMeals.sort((meal1, meal2) -> meal1.getDateTime().compareTo(meal2.getDateTime()));
        return allMeals;
    }
}

