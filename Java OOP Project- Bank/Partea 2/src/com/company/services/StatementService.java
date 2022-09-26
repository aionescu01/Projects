package com.company.services;

import com.company.entities.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StatementService {
    private List<Statement> statements = new ArrayList<>();
    private static StatementService instance;

    private StatementService() {
    }

    public static StatementService getInstance() {
        if (instance == null) {
            instance = new StatementService();
        }
        return instance;
    }


    public List<Statement> getstatements() {
        return new ArrayList<>(this.statements);
    }

    public Statement getStatementById(int id) {
        Statement st = new Statement();
        for (Statement p : this.statements)
            if (p.getId() == id)
                st = p;
        return st;
    }

    public void updateStatement(int id, Statement Statement) {
        for (Statement p : this.statements)
            if (p.getId() == id) {
                this.statements.remove(p);
                Statement.setId(id);
                this.statements.add(Statement);
                return;
            }
    }

    public void addStatement(Statement Statement) {
        this.statements.add(Statement);
    }

    public void deleteStatementById(int id) {
        for (Statement p : this.statements)
            if (p.getId() == id) {
                this.statements.remove(p);
                return;
            }
    }

    public void deletestatements() {
        this.statements.clear();
    }

    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))) {

            String line;
            var headerLine = in.readLine();
            while((line = in.readLine()) != null ) {
                //String[] fields = line.replaceAll(" ", "").split(",");
                String[] fields = line.replaceAll(" n ", "\n").split(";");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("No saved statements!");
        }

        return columns;
    }

    public void loadFromCSV() {
        AccountService accountService = AccountService.getInstance();
        PersonService personService = PersonService.getInstance();
        try{
            var columns = StatementService.getCSVColumns("src/com/company/data/statements.csv");
            for(var fields : columns){

                Account a = accountService.getAccountById(Integer.parseInt(fields[0]));

                var newAccount = new Statement(
                        a
                );
                newAccount.setStatement(fields[1]);
                statements.add(newAccount);
                personService.AddStatement(a,newAccount);

            }
        }catch (DateTimeParseException e){
            System.out.println(e.toString());
        }
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("src/com/company/data/statements.csv");
            for(var customer : this.statements){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
