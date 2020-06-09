/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.inter;

import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;
import java.util.List;

/**
 *
 * @author User
 */
public interface UserDaoInter {
    
    public List<User> getAll(String name, String surname, Integer nationalityId);
    
    public User findByEmailAndPassword(String email, String password);
    
    public User findByEmail(String email);
    
    public User getById(int userId);
    
    public boolean addUser(User u);
    
    public boolean updateUser(User u);
    
    public boolean removeUser(int id);
    
}
