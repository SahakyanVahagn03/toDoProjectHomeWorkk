package com.example.todoprojecthomework.servlet;


import com.example.todoprojecthomework.manager.UserManager;
import com.example.todoprojecthomework.model.User;
import com.example.todoprojecthomework.util.FileUploadUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(urlPatterns = "/register")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10,
        fileSizeThreshold = 1024 * 1024 * 1
)

public class UserRegisterServlet extends HttpServlet {


    private final UserManager USER_MANAGER = new UserManager();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Part picture = req.getPart("picture");
        String picName = FileUploadUtil.uploadImg(picture);
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            req.getSession().setAttribute("msg", "login or password is invalid");
            resp.sendRedirect("/register");
        } else {
            if (USER_MANAGER.getUserByEmail(email) == null) {
                USER_MANAGER.add(User.builder()
                        .name(name)
                        .surname(surname)
                        .email(email)
                        .password(password)
                        .picture(picName)
                        .build());
                req.getSession().setAttribute("msg", "registration finished successfully,  try to enter the profile");
                resp.sendRedirect("/login");
            } else {
                req.getSession().setAttribute("msg", "this email address already exist");
                resp.sendRedirect("/register");
            }
        }
    }
}
