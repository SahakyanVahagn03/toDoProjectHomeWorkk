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

@WebServlet(urlPatterns = "/addToDo")
public class AddToDoServlet extends HttpServlet {
    private final ToDoManager TO_DO_MANAGER = new ToDoManager();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String finishDate = req.getParameter("finishDate");
        User user = (User) req.getSession().getAttribute("user");
        if (title.trim().isEmpty() || finishDate.trim().isEmpty()) {
            req.getSession().setAttribute("msg", "you must write a <to do> title and choose finished date of your <to do>");
            resp.sendRedirect("/myToDo");
        } else {
            try {
                TO_DO_MANAGER.add(
                        ToDo.builder()
                                .title(title)
                                .createdDate(new Date())
                                .finishDate(DateUtil.stringToDate(finishDate))
                                .status(Status.NEW)
                                .user(user)
                                .build()
                );
                resp.sendRedirect("/myToDo");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
