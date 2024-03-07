package com.andersen.deploy.model;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private int age;
    private String surname;

    public User(int age, String surname) {
        this.age = age;
        this.surname = surname;
    }

    public User(int id, int age, String surname) {
        this.id = id;
        this.age = age;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", surname='" + surname + '\'' +
                '}';
    }
}
