package com.company.services;

import com.company.entities.Account;
import com.company.entities.Person;
import com.company.entities.Transaction;
import com.company.entities.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))) {
            var headerLine = in.readLine();
            String line;

            while((line = in.readLine()) != null ) {
                //String[] fields = line.replaceAll(" ", "").split(",");
                String[] fields = line.replaceAll("T", " ").split(";");
                //fields = line.replaceAll("\"", " ").split(";");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("No saved users!");
        }

        return columns;
    }

    public void loadFromCSV() {
        AccountService accountService = AccountService.getInstance();
        PersonService personService = PersonService.getInstance();
            var columns = UserService.getCSVColumns("src/com/company/data/users.csv");
            for(var fields : columns){

                Person p = personService.getPersonById(Integer.parseInt(fields[0]));
                var newAccount = new User(
                        p,
                        fields[1],
                        fields[2],
                        fields[3]
                );
                Users.add(newAccount);
            }
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("src/com/company/data/users.csv");
            for(var customer : this.Users){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
