//package com.mycompany.resumewebapp;
//
//import com.mycompany.dao.inter.UserDaoInter;
//import com.mycompany.entity.User;
//import com.mycompany.main.Context;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class UserRequestUtil {
//
//    public static void checkRequest(HttpServletRequest request, HttpServletResponse response) throws IllegalArgumentException {
//        String userIdStr = request.getParameter("id");
//        if (userIdStr == null || userIdStr.trim().isEmpty()) {
////              request.setAttribute("msg", "specify id");
//            throw new IllegalArgumentException("id is not valid");
//        }
//    }
//
//    public static User processRequest(HttpServletRequest request, HttpServletResponse response) {
//        UserRequestUtil.checkRequest(request, response);
//        Integer userId = Integer.parseInt(request.getParameter("id"));
//        UserDaoInter userDao = Context.instanceUserDao();
//        User u = userDao.getById(userId);
//        if(u==null){
//            throw new IllegalArgumentException("There is no user found with this id");
//        }
//        return u;
//    }
//
//}
