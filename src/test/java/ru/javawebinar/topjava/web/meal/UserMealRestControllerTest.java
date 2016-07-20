package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
/**
 * Created by jager on 20.07.16.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$").isArray())

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