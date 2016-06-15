package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.to.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepositoryImpl;
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
public class CreateMealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(CreateMealServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LOG.debug("redirect to createMealServlet description = " + request.getParameter("description"));

//LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        UserMealRepository mealDao = new InMemoryUserMealRepositoryImpl();
     /*   LOG.debug(request.getParameter("datetime").getClass().toString());
        LOG.debug(request.getParameter("datetime"));*/
        mealDao.save(new UserMeal(LocalDateTime.parse(request.getParameter("datetime")), request.getParameter("description"), Integer.parseInt(request.getParameter("calories")), 1));
   /*   Map<Integer, UserMealWithExceed> mealsWithExceed = mealDao.getMealWithExceed(2000);
        LOG.debug("size of mealsWithExceed " + mealsWithExceed.size());
        request.setAttribute("mealMap", mealsWithExceed);
   */     response.sendRedirect(request.getContextPath() + "/meals");

    }
}
