package com.company.entities;

public class User {
    //user care are persoana si date de login si rol
    private Person user_details;
    private String username;
    private String password;
    private String role;
    private static int number_of_users=0;
    private int user_id;

    public User(){}

    public User(Person user_details, String username, String password, String role) {
        this.user_details = user_details;
        this.username = username;
        this.password = password;
        this.role = role;
        this.user_id=number_of_users;
        number_of_users++;
    }

    public static int getNumber_of_users() {
        return number_of_users;
    }

    public static void setNumber_of_users(int number_of_users) {
        User.number_of_users = number_of_users;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Person getUser_details() {
        return user_details;
    }

    public void setUser_details(Person user_details) {
        this.user_details = user_details;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
