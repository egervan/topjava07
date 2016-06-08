package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Jager on 09.06.2016.
 */
public class CreateMealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(CreateMealServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.debug("redirect to createMealServlet description = " + request.getParameter("description"));

//LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        MealDao mealDao = new MealDaoImpl();
     /*   LOG.debug(request.getParameter("datetime").getClass().toString());
        LOG.debug(request.getParameter("datetime"));*/
        mealDao.createMeal(new UserMeal(LocalDateTime.parse(request.getParameter("datetime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
        Map<Integer, UserMealWithExceed> mealsWithExceed = mealDao.getMealWithExceed(2000);
        LOG.debug("size of mealsWithExceed " + mealsWithExceed.size());
        request.setAttribute("mealMap", mealsWithExceed);
        response.sendRedirect(request.getContextPath() + "/meals");

    }
}
