/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.entity.Country;
import com.mycompany.entity.Skill;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {

    @Override
    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("SELECT * FROM skill");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                skills.add(new Skill(id, name));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return skills;
    }

}
