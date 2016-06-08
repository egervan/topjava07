package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Jager on 08.06.2016.
 */
public class MealDaoImpl implements MealDao {

    private static volatile Map<Integer, UserMeal> mapMeal;

    public MealDaoImpl() {
        if(mapMeal == null)
        {
            mapMeal = UserMealsUtil.getAllMeal()
                    .stream()
                    .collect(Collectors
                    .toMap(UserMeal::getId, userMeal -> userMeal));
        }
    }/*public MealDaoImpl(List<UserMeal> mealList) {
       if(mapMeal == null)
       {
           mapMeal = mealList.stream()
                   .collect(Collectors
                           .toMap(UserMeal::getId, userMeal -> userMeal));
       }
    }*/

    @Override
    public synchronized void createMeal(UserMeal userMeal) {
        mapMeal.put(userMeal.getId(), userMeal);
    }

    @Override
    public synchronized UserMeal readMeal(int id) {
        return mapMeal.get(id);
    }

    @Override
    public synchronized void updateMeal(UserMeal userMeal) {
        mapMeal.put(userMeal.getId(), userMeal);
    }

    @Override
    public synchronized void delete(int id) {
        mapMeal.remove(id);
    }

    @Override
    public Map<Integer, UserMeal> getAllMeals() {
        return mapMeal;
    }

    @Override
    public Map<Integer, UserMealWithExceed> getMealWithExceed(int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mapMeal.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(
                        Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );
        return mapMeal.entrySet().stream()
                .map(Map.Entry::getValue)
                .map(um -> UserMealsUtil.createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toMap(meal -> meal.getId(), meal -> meal));
    }
}
