package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> resultList = new ArrayList<>();
        Collections.sort(mealList, (o1, o2) -> o1.getDateTime().toLocalDate().compareTo(o2.getDateTime().toLocalDate()));

        Map<LocalDate, List<UserMeal>> mapMeals = new HashMap<>();
        Iterator<UserMeal> iterator = mealList.iterator();

        //Проходим итератором по всем записям mealList и добавляем записи соответствующие условию в mapMeals
        while(iterator.hasNext())
        {
            UserMeal meal = iterator.next();

            {
                if (!mapMeals.containsKey(meal.getDateTime().toLocalDate()))
                {
                    mapMeals.put(meal.getDateTime().toLocalDate(), new ArrayList<>());
                }
                mapMeals.get(meal.getDateTime().toLocalDate()).add(new UserMeal(meal.getDateTime(), meal.getDescription(), meal.getCalories()));
            }
        }

        //Проходим по всем записям mapMeals, считаем калории за каждый день в первом внутреннем цикле, затем если количество countCalories >  caloriesPerDay
        //присваиваем isExceed - значение true а затем пишем нашу еду в resultList
        for(Map.Entry<LocalDate, List<UserMeal>> entry : mapMeals.entrySet())
        {
            int countCalories = 0;
            for(UserMeal meal : entry.getValue())
            {
                countCalories += meal.getCalories();
            }

            boolean isExceed = false;
            if(countCalories > caloriesPerDay) isExceed = true;

            for(UserMeal meal : entry.getValue())
            {
                if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                resultList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExceed));
            }
        }

        return resultList;
    }
}
