package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepositoryImpl;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;
  //  private UserMealRepository repository;
    private UserMealRestController mealController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
   //     repository = new InMemoryUserMealRepositoryImpl();
        mealController = appCtx.getBean(UserMealRestController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        UserMeal userMeal = mealController.create(new UserMeal(id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                LoggedUser.id()));
        LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
      //  repository.create(userMeal);

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", mealController.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
        else if(action.equals("filter"))
        {
            String startTimeParam = request.getParameter("startTime");
            String endTimeParam = request.getParameter("endTime");
            String startDateParam = request.getParameter("startDate");
            String endDateParam = request.getParameter("endDate");

            LocalTime startTime = startTimeParam.isEmpty() ? null : LocalTime.parse(startTimeParam);
            LocalTime endTime = endTimeParam.isEmpty() ? null : LocalTime.parse(endTimeParam);
            LocalDate startDate = startDateParam.isEmpty() ? null : LocalDate.parse(startDateParam);
            LocalDate endDate = endDateParam.isEmpty() ? null : LocalDate.parse(endDateParam);

            request.setAttribute("mealList", mealController.getBetweenDateTime(startDate, endDate, startTime, endTime));
            request.getRequestDispatcher("mealList.jsp").forward(request, response);
        }
        else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
      //      repository.delete(id);
            mealController.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("create") || action.equals("update")) {
      //      final UserMeal meal = action.equals("create") ?
      //              new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
      //              repository.get(getId(request));
      //      request.setAttribute("meal", meal);
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000, LoggedUser.id()) : mealController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }
}
