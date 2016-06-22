package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int ID_MEAL_1 = START_SEQ + 2;
    public static final int ID_MEAL_2 = START_SEQ + 3;
    public static final int ID_MEAL_3 = START_SEQ + 4;
    public static final int ID_MEAL_4 = START_SEQ + 5;
    public static final int ID_MEAL_5 = START_SEQ + 6;
    public static final int ID_MEAL_6 = START_SEQ + 7;

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final UserMeal MEAL_1 = new UserMeal(ID_MEAL_1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final UserMeal MEAL_2 = new UserMeal(ID_MEAL_2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final UserMeal MEAL_3 = new UserMeal(ID_MEAL_3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final UserMeal MEAL_4 = new UserMeal(ID_MEAL_4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    public static final UserMeal MEAL_5 = new UserMeal(ID_MEAL_5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    public static final UserMeal MEAL_6 = new UserMeal(ID_MEAL_6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);


}
