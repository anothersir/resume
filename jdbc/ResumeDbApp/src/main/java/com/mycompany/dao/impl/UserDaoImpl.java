/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.entity.Country;
import com.mycompany.entity.*;
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
    private User getUser(ResultSet rs) throws SQLException {
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
    
    @SneakyThrows
    private User getUserSimple(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        int birthPlace_id = rs.getInt("birthPlace_id");
        int nationality_id = rs.getInt("nationality_id");
        Date birthDate = rs.getDate("birthdate");
        String profileDesc = rs.getString("profile_description");
        String address = rs.getString("address");

        return new User(id, name, surname, email, phone, birthDate, null, null, profileDesc, address);
    }
    
    @SneakyThrows
    @Override
    public List<User> getAll(String name, String surname, Integer nationalityId) {
        List<User> result = new ArrayList<>();
        try ( Connection c = connect();) {

            String sql = "SELECT"
                    + "	u.*,"
                    + "	n.`name` AS birthplace,"
                    + "	c.nationality "
                    + "FROM"
                    + "	USER u"
                    + "	LEFT JOIN country n ON u.nationality_id = n.id"
                    + "	LEFT JOIN country c ON u.birthplace_id = c.id where 1=1";

            if(name!=null && !name.trim().isEmpty()){
                sql += " and u.name = ?";
            }

            if(surname!=null && !surname.trim().isEmpty()){
                sql += " and u.surname = ?";
            }

            if(nationalityId!=null){
                sql += " and u.nationality_id = ?";
            }

            PreparedStatement stmt = c.prepareStatement(sql);

            int i = 1;

            if(name != null && !name.trim().isEmpty()){
                stmt.setString(i, name);
                i++;
            }

            if(surname != null && !surname.trim().isEmpty()){
                stmt.setString(i, surname);
                i++;
            }

            if(nationalityId != null){
                stmt.setInt(i, nationalityId);
            }



            stmt.execute();
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

    @SneakyThrows
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

    @SneakyThrows
    @Override
    public boolean updateUser(User u) {
        try ( Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("update user set name = ?, "
                    + "surname = ?, email = ?, phone = ?, profile_description = ?,"
                    + " birthdate = ?, address = ?, birthplace_id = ?  where id = ?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getProfileDesc());
            stmt.setDate(6, u.getBirthDate());
            stmt.setString(7, u.getAddress());
            stmt.setInt(8, u.getBirthplace().getId());
            stmt.setInt(9, u.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @SneakyThrows
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
    
    private BCrypt.Hasher crypt = BCrypt.withDefaults();
    
    @SneakyThrows
    @Override
    public boolean addUser(User u) {

        try ( Connection c = connect();) {
            PreparedStatement stmt = c.prepareStatement("insert into user(name, surname, email, password, phone, profile_description) values(?,?,?,?)");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, crypt.hashToString(4, u.getPassword().toCharArray()));
            stmt.setString(5, u.getPhone());
            stmt.setString(6, u.getProfileDesc());
            return stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User result = null;
        try (Connection c = connect()){
            PreparedStatement stmt = c.prepareStatement("select * from user where email = ? and password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = getUserSimple(rs);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User findByEmail(String email) {
        User result = null;
        try (Connection c = connect()){
            PreparedStatement stmt = c.prepareStatement("select * from user where email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                result = getUserSimple(rs);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
