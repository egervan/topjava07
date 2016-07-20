package ru.javawebinar.topjava.web.meal;

import com.jayway.jsonassert.impl.JsonAsserterImpl;
import org.junit.Test;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import com.jayway.jsonassert.*;

/**
 * Created by jager on 20.07.16.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserMealRestController.REST_URL + '/';

    private ModelMatcher<UserMealWithExceed, String> matcher = new ModelMatcher<>(mealWithExceed -> mealWithExceed.toString());

    /*new ModelMatcher(new Function<UserMealWithExceed, UserMeal>() {
        @Override
        public UserMeal apply(UserMealWithExceed mealWithExceed) {
            return new UserMeal(mealWithExceed.getDateTime(), mealWithExceed.getDescription(), mealWithExceed.getCalories());
        }
    });*/
    @Test
    public void getAll() throws Exception {
        List<UserMealWithExceed> listMealWithExceed = UserMealsUtil.getWithExceeded(MealTestData.USER_MEALS, AuthorizedUser.getCaloriesPerDay());
        List<UserMealWithExceed> resultList;

        MvcResult result;
        result = mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$").isArray())
           //     .andExpect(jsonPath("$.[0]", is(JsonUtil.writeValue(listMealWithExceed.get(0)))))
                .andExpect(MATCHER.assertCollectionEquals(listMealWithExceed, JsonUtil.readValues(jsonPath("$"), UserMealWithExceed.class)))
                .andReturn();



                /*
                //JsonUtil.readValues(jsonPath("$").toString(), UserMealWithExceed.class)
                .andExpect((jsonPath("$.[0].description").exists()))
                .andExpect((jsonPath("$.[1].description").exists()))
                .andExpect((jsonPath("$.[2].description").exists()))
                .andExpect((jsonPath("$.[3].description").exists()))
                .andExpect((jsonPath("$.[4].description").exists()))
                .andExpect((jsonPath("$.[5].description").exists()))

                .andExpect((jsonPath("$.[0].dateTime").exists()))
                .andExpect((jsonPath("$.[1].dateTime").exists()))
                .andExpect((jsonPath("$.[2].dateTime").exists()))
                .andExpect((jsonPath("$.[3].dateTime").exists()))
                .andExpect((jsonPath("$.[4].dateTime").exists()))
                .andExpect((jsonPath("$.[5].dateTime").exists()))

                .andExpect((jsonPath("$.[0].calories").exists()))
                .andExpect((jsonPath("$.[1].calories").exists()))
                .andExpect((jsonPath("$.[2].calories").exists()))
                .andExpect((jsonPath("$.[3].calories").exists()))
                .andExpect((jsonPath("$.[4].calories").exists()))
                .andExpect((jsonPath("$.[5].calories").exists()))

                .andExpect((jsonPath("$.[0].id").exists()))
                .andExpect((jsonPath("$.[1].id").exists()))
                .andExpect((jsonPath("$.[2].id").exists()))
                .andExpect((jsonPath("$.[3].id").exists()))
                .andExpect((jsonPath("$.[4].id").exists()))
                .andExpect((jsonPath("$.[5].id").exists()))
                ;
                //                .andExpect((jsonPath("$.[0].description", is(listMealWithExceed.get(0).getDescription())))


                */
    }

    @Test
    public void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(MEAL1.getId())))
                .andExpect(jsonPath("$.dateTime", is(MEAL1.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME))))
                .andExpect(jsonPath("$.description", is(MEAL1.getDescription())))
                .andExpect(jsonPath("$.calories", is(MEAL1.getCalories()))
                );
    }

    @Test
    public void createMeal() throws Exception {
        UserMeal meal = MealTestData.getCreated();
        String jsonMeal = JsonUtil.writeValue(meal);

/*
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(REST_URL + MEAL1_ID);
        request.contentType(MediaType.APPLICATION_JSON);
        request.content(jsonMeal);
*/

        mockMvc.perform(/*request*/MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMeal))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void update() throws Exception {
        UserMeal meal = getUpdated();
        String jsonMeal = JsonUtil.writeValue(meal);

     /*   MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(REST_URL + meal.getId());
        request.contentType(MediaType.APPLICATION_JSON);
        request.content(jsonMeal);
*/
        mockMvc.perform(/*request*/MockMvcRequestBuilders.put(REST_URL + meal.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMeal))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


    }

    @Test
    public void getBetween() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "between").param("start", "2015-05-31T00:00:00").param("end", "2015-05-31T23:59:59"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                ;
    }

}