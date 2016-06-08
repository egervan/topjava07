package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Jager on 08.06.2016.
 */
public class DeleteMealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(DeleteMealServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to deleteMealServlet id = " + request.getParameter("id"));


        MealDao mealDao = new MealDaoImpl();
        mealDao.delete(Integer.parseInt(request.getParameter("id")));
        Map<Integer, UserMealWithExceed> mealsWithExceed = mealDao.getMealWithExceed(2000);
        LOG.debug("size of mealsWithExceed " + mealsWithExceed.size());
        request.setAttribute("mealMap", mealsWithExceed);
        response.sendRedirect(request.getContextPath() + "/meals");

    }
}
