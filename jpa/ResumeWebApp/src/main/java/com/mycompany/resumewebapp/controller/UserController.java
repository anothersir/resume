/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.resumewebapp.controller;

import com.mycompany.dao.impl.UserDaoImpl;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.User;
import com.mycompany.main.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author User
 */
@WebServlet(name = "UserController", urlPatterns = {"/users"})
public class UserController extends HttpServlet {

    private UserDaoInter userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDaoInter userDao = Context.instanceUserDao();

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        String nationalityIdStr = request.getParameter("nationalityId");
        Integer nationalityId = null;
        if (nationalityIdStr != null && !nationalityIdStr.trim().isEmpty()) {
            nationalityId = Integer.parseInt(nationalityIdStr);
        }

        List<User> list = userDao.getAll(name, surname, nationalityId);
        request.setAttribute("userlist", list);
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }

}
