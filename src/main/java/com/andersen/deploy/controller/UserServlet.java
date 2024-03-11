package com.andersen.deploy.controller;

import com.andersen.deploy.dao.UserUserDaoImpl;
import com.andersen.deploy.exception.ServiceException;
import com.andersen.deploy.model.User;
import com.andersen.deploy.service.UserService;
import com.andersen.deploy.service.UserServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl(new UserUserDaoImpl());
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        if (req.getParameterMap().size() == 0) {
            List<User> users = null;
            try {
                users = userService.findAllUsers();
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
            out.print(gson.toJson(users));
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = null;
            try {
                user = userService.findById(id);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
            out.print(gson.toJson(user));
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader in = req.getReader();
        String jsonUser = in.lines().collect(Collectors.joining());
        User user = gson.fromJson(jsonUser, User.class);
        try {
            userService.create(user);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader in = req.getReader();
        String jsonUser = in.lines().collect(Collectors.joining());
        User user = gson.fromJson(jsonUser, User.class);
        try {
            userService.update(user);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            userService.delete(id);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}