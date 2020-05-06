/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.entity.Country;
import com.mycompany.entity.Skill;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserDaoInter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;

/**
 *
 * @author User
 */
public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

    @SneakyThrows
    private User getUser(ResultSet rs) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        int birthPlace_id = rs.getInt("birthPlace_id");
        int nationality_id = rs.getInt("nationality_id");
        String birthPlaceStr = rs.getString("birthplace");
        String nationalityStr = rs.getString("nationality");
        Date birthDate = rs.getDate("birthdate");
        String profileDesc = rs.getString("profile_description");
        String address = rs.getString("address");

        Country nationality = new Country(nationality_id, null, nationalityStr);
        Country birthplace = new Country(birthPlace_id, birthPlaceStr, null);

        return new User(id, name, surname, email, phone, birthDate, nationality, birthplace, profileDesc, address);
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try ( Connection c = connect();) {
            Statement stmt = c.createStatement();
            stmt.execute("SELECT"
                    + "	u.*,"
                    + "	n.`name` AS birthplace,"
                    + "	c.nationality "
                    + "FROM"
                    + "	USER u"
                    + "	LEFT JOIN country n ON u.nationality_id = n.id"
                    + "	LEFT JOIN country c ON u.birthplace_id = c.id");
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                User u = getUser(rs);
                result.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public User getById(int userId) {
        User result = null;
        try ( Connection c = connect();) {
            Statement stmt = c.createStatement();
            stmt.execute("SELECT"
                    + "	u.*,"
                    + "	n.`name` AS birthplace,"
                    + "	c.nationality "
                    + "FROM"
                    + "	USER u"
                    + "	LEFT JOIN country n ON u.nationality_id = n.id"
                    + "	LEFT JOIN country c ON u.birthplace_id = c.id WHERE u.id = " + userId);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getUser(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {
        try ( Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("update user set name = ?, "
                    + "surname = ?, email = ?, phone = ?, profile_description = ?, birthdate = ?, address = ? where id = ?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getProfileDesc());
            stmt.setDate(6, u.getBirthDate());
            stmt.setString(7, u.getAddress());
            stmt.setInt(8, u.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUser(int id) {
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            return stmt.execute("delete from user where id = " + id);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUser(User u) {

        try ( Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("insert into user(name, surname, email, phone, profile_description) values(?,?,?,?)");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getProfileDesc());
            return stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
