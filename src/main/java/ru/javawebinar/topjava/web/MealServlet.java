package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by nz on 08.06.16.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        List<UserMeal> mealWithoutExceed = UserMealsUtil.getAllMeal();
        List<UserMealWithExceed> mealsWithExceed = UserMealsUtil.getFilteredWithExceeded(mealWithoutExceed, LocalTime.MIN, LocalTime.MAX, 2000);
//        request.getRequestDispatcher("/userList.jsp").forward(request, response);
        LOG.debug("size of mealsWithExceed " + mealsWithExceed.size());
        request.setAttribute("mealList", mealsWithExceed);
        try
        {
            getServletConfig().getServletContext().getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
        catch (ServletException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
