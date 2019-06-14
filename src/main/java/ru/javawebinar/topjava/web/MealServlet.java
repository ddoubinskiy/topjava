package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.local.MealDAOLocalImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet
{
    private MealDAO repository = new MealDAOLocalImpl();
    private static final String ADD_EDIT = "/mealForm.jsp";
    private static final String LIST = "/meals.jsp";
    private static final String SERVLET = "meals";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String action = req.getParameter("action");
        String requestDispatcherPath = LIST;
        if (action != null && action.equalsIgnoreCase("edit"))
        {
            String idStr = req.getParameter("id");
            if (idStr != null)
            {
                Meal meal = repository.get(Long.parseLong(idStr));
                req.setAttribute("meal", meal);
                requestDispatcherPath = ADD_EDIT;
            }
        } else
        {
            if (action != null && action.equalsIgnoreCase("delete"))
            {
                String idStr = req.getParameter("id");
                if (idStr != null)
                {
                    repository.delete(Long.parseLong(idStr));
                    resp.sendRedirect(SERVLET);
                    return;
                }
            }
            int caloriesPerDay = 2000;
            List<MealTo> filteredWithExcess = MealsUtil.getFilteredWithExcess(repository.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
            req.setAttribute("excessValue", caloriesPerDay);
            req.setAttribute("list", filteredWithExcess);
        }

        req.getRequestDispatcher(requestDispatcherPath).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        if (description != null && dateTime != null)
        {
            Meal newMeal = new Meal(dateTime, description, calories);
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty())
            {
                repository.update(Long.parseLong(idStr), newMeal);
            } else
            {
                repository.add(newMeal);
            }
        }
        int caloriesPerDay = 2000;
        List<MealTo> filteredWithExcess = MealsUtil.getFilteredWithExcess(repository.getAll(), LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
        req.setAttribute("excessValue", caloriesPerDay);
        req.setAttribute("list", filteredWithExcess);
        req.getRequestDispatcher(LIST).forward(req, resp);
    }
}
