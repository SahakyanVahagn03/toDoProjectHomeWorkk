package com.example.todoprojecthomework.servlet;


import com.example.todoprojecthomework.manager.ToDoManager;
import com.example.todoprojecthomework.model.Status;
import com.example.todoprojecthomework.model.ToDo;
import com.example.todoprojecthomework.model.User;
import com.example.todoprojecthomework.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@WebServlet(urlPatterns = "/changeToDo")
public class ChangeToDoServlet extends HttpServlet {
    private final ToDoManager TO_DO_MANAGER = new ToDoManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String toDoId = req.getParameter("toDoId");
        User user = (User) req.getSession().getAttribute("user");
        if (!toDoId.chars().allMatch(Character::isDigit)) {
            resp.sendRedirect("/myToDo");
        } else {
            ToDo toDo = TO_DO_MANAGER.getToDoWithUserIdAndToDoId(user.getId(), Integer.parseInt(toDoId));
            if (toDo == null) {
                resp.sendRedirect("/myToDo");
            } else {
                req.setAttribute("toDo", toDo);
                req.getRequestDispatcher("/WEB-INF/changeToDo.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String toDoId = req.getParameter("toDoId");
        String title = req.getParameter("title");
        String finishDate = req.getParameter("finishDate");
        User user = (User) req.getSession().getAttribute("user");

        if (title.trim().isEmpty() || finishDate.trim().isEmpty() ||
                !(toDoId.chars().allMatch(Character::isDigit))) {
            req.getSession().setAttribute("msg", "you must write a <to do> title and choose finished date of your <to do>");
            resp.sendRedirect("/myToDo");
        } else {
            ToDo toDo = TO_DO_MANAGER.getToDoWithUserIdAndToDoId(user.getId(), Integer.parseInt(toDoId));
            if (toDo != null) {
                try {
                    toDo.setTitle(title);
                    toDo.setFinishDate(DateUtil.stringToDate(finishDate));
                    TO_DO_MANAGER.changeToDo(toDo);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            resp.sendRedirect("/myToDo");
        }
    }
}
