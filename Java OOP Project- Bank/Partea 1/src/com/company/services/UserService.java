package com.company.services;

import com.company.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> Users = new ArrayList<>();
    private static UserService instance;

    private UserService(){}

    public static UserService getInstance(){
        if(instance==null){
            instance=new UserService();
        }
        return instance;
    }


    public List<User> getUsers(){
        return new ArrayList<>(this.Users);
    }

    public User getUserById(int id){
        User User = new User();
        for(User p : this.Users)
            if(p.getUser_id()==id)
                User=p;
        return User;
    }

    public void updateUser(int id, User User){
        for(User p : this.Users)
            if(p.getUser_id()==id)
            {
                this.Users.remove(p);
                User.setUser_id(id);
                this.Users.add(User);
                return;
            }
    }

    public void addUser(User User){
        this.Users.add(User);
    }

    public void deleteUserById(int id){
        for(User p : this.Users)
            if(p.getUser_id()==id)
            {
                this.Users.remove(p);
                return;
            }
    }

    public void deleteUsers(){
        this.Users.clear();
    }
}
