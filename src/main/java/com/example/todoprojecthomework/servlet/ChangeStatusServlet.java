package com.example.todoprojecthomework.servlet;

import com.example.todoprojecthomework.manager.ToDoManager;
import com.example.todoprojecthomework.model.Status;
import com.example.todoprojecthomework.model.ToDo;
import com.example.todoprojecthomework.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/changeStatus")
public class ChangeStatusServlet extends HttpServlet {
    private final ToDoManager TO_DO_MANAGER = new ToDoManager();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String toDoId = req.getParameter("toDoId");
        User user = (User) req.getSession().getAttribute("user");
        if (!toDoId.chars().allMatch(Character::isDigit)){
            req.getSession().setAttribute("msg", "invalid field");
            resp.sendRedirect("/myToDo");
        }else {
            ToDo toDo = TO_DO_MANAGER.getToDoWithUserIdAndToDoId(user.getId(), Integer.parseInt(toDoId));
            if (toDo != null){
                if (toDo.getStatus() == Status.NEW){
                    TO_DO_MANAGER.changeToDoStatus(Status.DONE,  Integer.parseInt(toDoId));
                }else {
                    TO_DO_MANAGER.changeToDoStatus(Status.NEW,  Integer.parseInt(toDoId));
                }//
            }
        }
    }
}
