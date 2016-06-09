package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Jager on 09.06.2016.
 */
public class UpdateMealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UpdateMealServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.debug("redirect to doPost updateMealServlet description = " );

        Dao mealDao = new MealDaoImpl();

        mealDao.update(new UserMeal(Integer.parseInt(request.getParameter("id")), LocalDateTime.parse(request.getParameter("datetime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
        Map<Integer, UserMealWithExceed> mealsWithExceed = mealDao.getMealWithExceed(2000);
        LOG.debug("size of mealsWithExceed " + mealsWithExceed.size());
        request.setAttribute("mealMap", mealsWithExceed);
        response.sendRedirect(request.getContextPath() + "/meals");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //LOG.debug("redirect to doGet updateMealServlet description = " + request.getParameter("meal"));

        Dao mealDao = new MealDaoImpl();

        UserMeal meal = mealDao.read(Integer.parseInt(request.getParameter("id")));

        request.setAttribute("meal", meal);
        try
        {
            getServletConfig().getServletContext().getRequestDispatcher("/updateMeal.jsp").forward(request, response);
        }
        catch (ServletException | IOException e)
        {
            e.printStackTrace();
        }

    }
}
