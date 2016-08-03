package ru.javawebinar.topjava.util.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.UserMeal;

/**
 * Created by jager on 03.08.16.
 */
@Component
public class CaloriesValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UserMeal.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserMeal meal = (UserMeal) o;
        int calories = meal.getCalories();
        if(calories == 0) errors.rejectValue("calories", "UserMeal.calories.empty", "calories can't be empty");
    }
}
