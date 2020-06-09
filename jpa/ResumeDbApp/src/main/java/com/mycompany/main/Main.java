/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.main;

import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.User;

/**
 *
 * @author User
 */
public class Main {

    public static void main(String[] args) throws Exception {
        
        UserDaoInter dao = Context.instanceUserDao();
        User u = dao.findByEmail("enver@gmail.com");
        System.out.println(" u = " + u);

    }

}
