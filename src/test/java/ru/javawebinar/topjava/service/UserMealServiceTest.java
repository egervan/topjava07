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
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.*;

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
    public void testGet() throws Exception {
        UserMeal resultMeal = service.get(ID_MEAL_1, LOGGED_USER_ID);
        UserMeal testMeal = MEAL_1;
        MATCHER.assertEquals(testMeal, resultMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundUser() throws Exception {
        service.get(ID_MEAL_1, NOT_EXIST_USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundMeal() throws Exception {
        service.get(ID_MEAL_NOT_EXIST, LOGGED_USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFoundMealOfForeignUser() throws Exception {
        service.get(ID_MEAL_NOT_EXIST, FOREIGN_USER_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ID_MEAL_1, LOGGED_USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFoundFood() throws Exception {
        service.delete(ID_MEAL_NOT_EXIST, LOGGED_USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFoundUser() throws Exception {
        service.delete(ID_MEAL_1, NOT_EXIST_USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteMealForeignUser() throws Exception {
        service.delete(ID_MEAL_1, FOREIGN_USER_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<UserMeal> expectedList = MealTestData.getAll().stream()
                                                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalDate(), START_DATE, END_DATE))
                                                .collect(Collectors.toList());
        Collection<UserMeal> resultList = service.getBetweenDates(START_DATE, END_DATE, LOGGED_USER_ID);
        MATCHER.assertCollectionEquals(expectedList, resultList);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        service.getBetweenDateTimes(startDateTime, endDateTime, LOGGED_USER_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(MealTestData.getAll(), service.getAll(LOGGED_USER_ID));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal testMeal = new UserMeal(MealTestData.MEAL_3);
        testMeal.setCalories(666);
        testMeal.setDescription("Smells food");

        service.update(testMeal, LOGGED_USER_ID);

        UserMeal updatedMeal = service.get(testMeal.getId(), LOGGED_USER_ID);

        MATCHER.assertEquals(testMeal, updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateForeignMeal() throws Exception {
        UserMeal testMeal = new UserMeal(MealTestData.MEAL_4);
        testMeal.setCalories(777);
        testMeal.setDescription("Foreign food");

        service.update(testMeal, FOREIGN_USER_ID);

        UserMeal updatedMeal = service.get(testMeal.getId(), FOREIGN_USER_ID);
        MATCHER.assertEquals(testMeal, updatedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFoundMeal() throws Exception {
        UserMeal testMeal = new UserMeal(MealTestData.MEAL_4);
        testMeal.setId(ID_MEAL_NOT_EXIST);
        testMeal.setCalories(777);
        testMeal.setDescription("Not exist meal");

        service.update(testMeal, FOREIGN_USER_ID);
/*
        UserMeal updatedMeal = service.get(testMeal.getId(), FOREIGN_USER_ID);
        MATCHER.assertEquals(testMeal, updatedMeal);*/
    }
    @Test
    public void testCreate() throws Exception {
        service.save(new UserMeal(LocalDateTime.of(2016, Month.JUNE, 22, 13, 33), "JustFood", 666), LOGGED_USER_ID);
    }

}