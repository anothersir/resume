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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import lombok.SneakyThrows;

/**
 *
 * @author User
 */
public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

    EntityManager em;

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

        EntityManager em = em();

        String jpql = "select u from User u where 1=1";

        List<User> result = new ArrayList<>();
//        try ( Connection c = connect();) {
//
//            String sql = "SELECT"
//                    + "	u.*,"
//                    + "	n.`name` AS birthplace,"
//                    + "	c.nationality "
//                    + "FROM"
//                    + "	USER u"
//                    + "	LEFT JOIN country n ON u.nationality_id = n.id"
//                    + "	LEFT JOIN country c ON u.birthplace_id = c.id where 1=1";

        if (name != null && !name.trim().isEmpty()) {
            jpql += " and u.name = :name";
        }

        if (surname != null && !surname.trim().isEmpty()) {
            jpql += " and u.surname = :surname";
        }

        if (nationalityId != null) {
            jpql += " and u.nationality.id = :nId";
        }
        
        jpql = jpql + " ORDER BY id ASC";

//            PreparedStatement stmt = c.prepareStatement(sql);
        Query q = em.createQuery(jpql, User.class);

        if (name != null && !name.trim().isEmpty()) {
            q.setParameter("name", name);
        }

        if (surname != null && !surname.trim().isEmpty()) {
            q.setParameter("surname", surname);
        }

        if (nationalityId != null) {
            q.setParameter("nId", nationalityId);
        }

//            stmt.execute();
//            ResultSet rs = stmt.getResultSet();
//
//            while (rs.next()) {
//                User u = getUser(rs);
//                result.add(u);
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
        return q.getResultList();
    }

    @SneakyThrows
    @Override
    public User getById(int userId) {
        EntityManager entityManager = em();
        User u = entityManager.find(User.class, userId);

        em.close();
        return u;
    }

    @SneakyThrows
    @Override
    public boolean updateUser(User u) {
        EntityManager entityManager = em();

        entityManager.find(User.class, 2);
        entityManager.getTransaction().begin();
        entityManager.merge(u);
        entityManager.getTransaction().commit();

        entityManager.close();
        return true;
    }

    @SneakyThrows
    @Override
    public boolean removeUser(int id) {
        EntityManager entityManager = em();

        User u = entityManager.find(User.class, 2);
        entityManager.getTransaction().begin();
        entityManager.remove(u);
        entityManager.getTransaction().commit();

        em.close();
        return true;
    }

    private BCrypt.Hasher crypt = BCrypt.withDefaults();

    @SneakyThrows
    @Override
    public boolean addUser(User u) {
        EntityManager entityManager = em();

        entityManager.find(User.class, 2);
        entityManager.getTransaction().begin();
        //       u.setPassword(crypt.hashToString(4, u.getPassword().toCharArray()));
        entityManager.persist(u);
        entityManager.getTransaction().commit();

        em.close();;
        return true;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {

        EntityManager em = em();

        //Bu methoda verdiyimiz ikinci paramether tipinde bir obyekte bilgileri doldurub return edecek
        Query q = em.createQuery("select u from User u where u.email = :e and u.password = :p", User.class);
        q.setParameter("e", email);
        q.setParameter("p", password);

        List<User> list = q.getResultList();

        if (list.size() == 1) {
            return list.get(0);
        }

        return null;

//        User result = null;
//        try ( Connection c = connect()) {
//            PreparedStatement stmt = c.prepareStatement("select * from user where email = ? and password = ?");
//            stmt.setString(1, email);
//            stmt.setString(2, password);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                result = getUserSimple(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
    }

    @Override
    public User findByEmail(String email) {

        EntityManager em = em();

        //Bu methoda verdiyimiz ikinci paramether tipinde bir obyekte bilgileri doldurub return edecek
        Query q = em.createQuery("select u from User u where u.email = :e", User.class);
        q.setParameter("e", email);

        List<User> list = q.getResultList();

        if (list.size() == 1) {
            return list.get(0);
        }

        return null;

//        User result = null;
//        try ( Connection c = connect()) {
//            PreparedStatement stmt = c.prepareStatement("select * from user where email = ?");
//            stmt.setString(1, email);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                result = getUserSimple(rs);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
    }

}
