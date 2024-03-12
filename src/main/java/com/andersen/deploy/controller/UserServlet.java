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
    private final Gson gson = new Gson();

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
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.print(gson.toJson(users));
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = null;
            try {
                user = userService.findById(id);
            } catch (ServiceException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.print(gson.toJson(user));
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader in = req.getReader();
        String jsonUser = in.lines().collect(Collectors.joining());
        User user = gson.fromJson(jsonUser, User.class);
        try {
            userService.create(user);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader in = req.getReader();
        String jsonUser = in.lines().collect(Collectors.joining());
        User user = gson.fromJson(jsonUser, User.class);
        try {
            userService.update(user);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            userService.delete(id);
        } catch (ServiceException | NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}