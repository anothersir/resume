/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

/**
 *
 * @author User
 */
public class Country {
    private int id;
    private String name;
    private String nationality;

    public Country() {
    }

    public Country(int id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return nationality;
    }

    public void setCountryName(String countryName) {
        this.nationality = countryName;
    }

    @Override
    public String toString() {
        return "Nationality{" + "id=" + id + ", name=" + name + ", nationality=" + nationality + '}';
    }
    
    
}
