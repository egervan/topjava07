package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.service.UserMealService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll() {
        LOG.info("getAll UserMealWithExceed");
        return service.getAll(LoggedUser.id(), LoggedUser.getCaloriesPerDay());
//        return getBetweenDateTime(LocalDateTime.MIN, LocalDateTime.MAX);
    }

    public List<UserMealWithExceed> getBetweenDateTime(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime)
    {
        if(startDate == null) startDate = LocalDate.MIN;
        if(endDate == null) endDate = LocalDate.MAX;

        LocalDateTime start = startDate.atTime(startTime == null ? LocalTime.MIN : startTime);
        LocalDateTime end = endDate.atTime(endTime == null ? LocalTime.MAX : endTime);
        return getBetweenDateTime(start, end);
    }
    public List<UserMealWithExceed> getBetweenDateTime(LocalDateTime start, LocalDateTime end)
    {
        LOG.info("getBetweenDateTime");
        if(start == null) start = LocalDateTime.MIN;
        if(end == null) end = LocalDateTime.MAX;
        return service.getBetweenDateTime(LoggedUser.id(), LoggedUser.getCaloriesPerDay(), start, end);
    }

    public UserMeal create(UserMeal userMeal) {
        userMeal.setId(null);
        LOG.info("create meal " + userMeal);
        return service.create(userMeal);
    }

    public UserMeal get(int id) {
        LOG.info("get meal " + id);
        return service.get(LoggedUser.id(), id);
    }

    public void delete(int id) {
        LOG.info("delete meal " + id);
        service.delete(LoggedUser.id(), id);
    }

    public void update(UserMeal userMeal) {
        LOG.info("update meal " + userMeal);
        service.update(userMeal);
    }





}
