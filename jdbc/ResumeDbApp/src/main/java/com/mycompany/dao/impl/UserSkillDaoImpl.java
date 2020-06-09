/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.entity.Skill;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserSkillDaoInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;

/**
 *
 * @author User
 */
public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter {

    @SneakyThrows
    private UserSkill getUserSkill(ResultSet rs) {
        int userId = rs.getInt("id");
        int userSkillId = rs.getInt("userSkillId");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        return new UserSkill(userSkillId, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try ( Connection c = connect();) {

            PreparedStatement stmt = c.prepareStatement("SELECT"
                    + " us.id as userSkillId, "
                    + "	u.*,"
                    + "	us.skill_id,"
                    + "	s.NAME AS skill_name,"
                    + "	us.power "
                    + "FROM"
                    + "	user_skill us"
                    + "	LEFT JOIN USER u ON us.user_id = u.id"
                    + "	LEFT JOIN skill s ON us.skill_id = s.id "
                    + "WHERE"
                    + "	us.user_id = ?;");
            stmt.setInt(1, userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserSkill u = getUserSkill(rs);
                result.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    @Override
    public boolean removeUserSkill(int id){
        try(Connection c = connect()){
            PreparedStatement stmt = c.prepareStatement("DELETE FROM user_skill WHERE id = ?");
            stmt.setInt(1, id);
            return stmt.execute();
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean addUserSkill(UserSkill userSkill) {
        try(Connection c = connect()){

            PreparedStatement stmt = c.prepareStatement("INSERT INTO user_skill(skill_id, user_id, power) VALUES (? ,? ,?)");
            stmt.setInt(1, userSkill.getSkill().getId());
            stmt.setInt(2, userSkill.getUser().getId());
            stmt.setInt(3, userSkill.getPower());
            return stmt.execute();
            
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateUserSkill(UserSkill skill){
        try(Connection c = connect()){
            PreparedStatement stmt = c.prepareStatement("UPDATE user_skill SET skill_id = ? , power = ?, user_id = ? WHERE id = ?");
            stmt.setInt(1, skill.getSkill().getId());
            stmt.setInt(2, skill.getPower());
            stmt.setInt(3, skill.getUser().getId());
            stmt.setInt(4, skill.getId());
            return stmt.execute();
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
