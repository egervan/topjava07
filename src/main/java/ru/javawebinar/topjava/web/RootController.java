package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;
    @Autowired
    private UserMealRestController mealController;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", service.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute("id") Integer id, Model model) {
        mealController.delete(id);
        return "redirect:meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@ModelAttribute("id") Integer id, Model model) {
        model.addAttribute("meal", mealController.get(id));
        return "mealEdit";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(HttpServletRequest request,/*, HttpServletResponse response*/ Model model) {
        if("delete".equals(request.getParameter("action")))
            return "forward:delete";
        else if("update".equals(request.getParameter("action")))
            return "forward:update";

        model.addAttribute("mealList", mealController.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String updateAction(HttpServletRequest request,/*, HttpServletResponse response*/ Model model) {
        final UserMeal userMeal = new UserMeal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        mealController.update(userMeal, getId(request));
        model.addAttribute("mealList", mealController.getAll());
        return "mealList";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }







}
