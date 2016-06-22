package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.ID_MEAL_1;
import static ru.javawebinar.topjava.MealTestData.ID_MEAL_NEW;
import static ru.javawebinar.topjava.MealTestData.MATCHER;

/**
 * Created by jager on 22.06.16.
 */
@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }


    @Test
    public void get() throws Exception {
        service.get(100002, 100000);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(100000, 1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundUser() throws Exception {
        service.get(1, 100003);
    }

    @Test
    public void delete() throws Exception {
        service.delete(100005, 100000);
    }

    @Test
    public void getBetweenDates() throws Exception {
        service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 31), 100000);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), 100000);
    }

    @Test
    public void getAll() throws Exception {
        service.getAll(100000);
    }

    @Test
    public void update() throws Exception {
        UserMeal testMeal = MealTestData.MEAL_3;
        testMeal.setCalories(666);
        testMeal.setDescription("Smells food");

        int userId = 100000;
        service.update(testMeal, userId);

        UserMeal updatedMeal = service.get(testMeal.getId(), userId);

        MATCHER.assertEquals(testMeal, updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeignMeal() throws Exception {
        UserMeal testMeal = MealTestData.MEAL_4;
        testMeal.setCalories(777);
        testMeal.setDescription("Foreign food");

        int userId = 100001;
        service.update(testMeal, userId);

        UserMeal updatedMeal = service.get(testMeal.getId(), userId);

        MATCHER.assertEquals(testMeal, updatedMeal);
    }

    @Test
    public void create() throws Exception {
        service.save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 22, 13, 33), "JustFood", 666), 100000);
    }

}