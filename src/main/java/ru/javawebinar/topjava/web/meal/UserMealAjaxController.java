package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by jager on 25.07.16.
 */
@RestController
@RequestMapping("/ajax/meals")
public class UserMealAjaxController extends AbstractUserMealController {

   /* @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll()
    {
        return super.getAll();
    }*/

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(value = "startTime", required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam(value = "endDate", required = false)   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,   @RequestParam(value = "endTime", required = false)    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable("id") Integer id)
    {
        return super.get(id);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Integer id)
    {
        super.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("description") String description,
                               @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                               @RequestParam("calories") Integer calories) {
        UserMeal meal = new UserMeal(id, dateTime, description, calories);
        if(meal.isNew()) {
            super.create(meal);
        }
        else {
            super.update(meal, meal.getId());
        }
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(UserMeal meal, Integer id)
    {
        super.update(meal, id);
    }
}
