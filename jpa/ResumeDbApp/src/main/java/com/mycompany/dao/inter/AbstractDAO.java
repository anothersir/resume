/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dao.inter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lombok.SneakyThrows;

/**
 *
 * @author User
 */
public abstract class AbstractDAO {

    @SneakyThrows
    public Connection connect(){

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:9856/resume?allowPublicKeyRetrieval=true&useSSL=false&verifyServerCertificate=false&requireSSL=false";
        String username = "root";
        String password = "another";
        Connection c = DriverManager.getConnection(url, username, password);

        return c;
    }
    
    //EntityManagerFactory nin acilib baglanmasi cok agir bir isdir. Ona gore 
    //is tam qutarannan sonra close elemek lazimdir
    private static EntityManagerFactory emf = null;
    
    public EntityManager em(){
        if(emf == null){
            emf= Persistence.createEntityManagerFactory("resumappPU");
        }
        EntityManager entityManager = emf.createEntityManager();
        return entityManager;
    }
    
    public void closeEmf(){
        emf.close();
    }
}
