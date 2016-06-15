package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        LOG.info("save meal " + userMeal);
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
        LOG.info("delete meal " + id);
        return repository.get(userId).remove(id) == null;
    }

    @Override
    public UserMeal get(int userId, int id) {
        LOG.info("get meal " + id);
        return repository.get(userId).get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        LOG.info("getAll meal");
        return getBetweenDateTime(userId, LocalDateTime.MIN, LocalDateTime.MAX);
        /*List<UserMeal> meals = new ArrayList(repository.get(userId).values());
        meals.sort((meal1, meal2) -> meal1.getDateTime().compareTo(meal2.getDateTime()));
        if(meals.isEmpty()) meals = Collections.emptyList();
        return meals;*/
    }

    @Override
    public Collection<UserMeal> getBetweenDateTime(int userId, LocalDateTime start, LocalDateTime end) {
        LOG.info("getBetweenDateTime meal");
        List<UserMeal> meals = repository.get(userId).values()
                .stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime(), start, end))
                .sorted((meal1, meal2) -> meal1.getDateTime().compareTo(meal2.getDateTime()))
                .collect(Collectors.toList());
        //        meals.sort((meal1, meal2) -> meal1.getDateTime().compareTo(meal2.getDateTime()));
        if(meals.isEmpty()) meals = Collections.emptyList();
        return meals;
    }
}

