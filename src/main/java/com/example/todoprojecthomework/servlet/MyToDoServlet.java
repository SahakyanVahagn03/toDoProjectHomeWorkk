package com.example.todoprojecthomework.servlet;


import com.example.todoprojecthomework.manager.ToDoManager;
import com.example.todoprojecthomework.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/myToDo")
public class MyToDoServlet extends HttpServlet {
    private final ToDoManager TO_DO_MANAGER = new ToDoManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("toDoList", TO_DO_MANAGER.getOwnToDoList(user));
        req.getRequestDispatcher("/WEB-INF/myToDo.jsp").forward(req, resp);

    }
}
