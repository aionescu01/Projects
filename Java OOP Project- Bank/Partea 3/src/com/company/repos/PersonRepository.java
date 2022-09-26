package com.company.repos;

import com.company.entities.Account;
import com.company.entities.Person;
import com.company.entities.Transaction;
import com.company.services.AccountService;
import com.company.services.DebitService;
import com.company.services.PersonService;
import com.company.services.SavingsService;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class PersonRepository {

    public void CreateTable() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao", "root", "root");
                Statement stmt = conn.createStatement();
        ) {
            String person_table = "CREATE TABLE People " +
                    "(person_id INTEGER not NULL, " +
                    " name VARCHAR(255), " +
                    " uid VARCHAR(255), " +
                    " address VARCHAR(255), " +
                    " account_ids VARCHAR(255), " +
                    " statement_history_ids VARCHAR(255), " +
                    " transaction_history_ids VARCHAR(255) , " +
                    " PRIMARY KEY ( person_id ))";
            stmt.executeUpdate(person_table);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addPerson(Person p){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String ids = "";
            int j=1;
            for(var i:p.getAccounts())
            {

                if(p.getAccounts().size()==j) {
                    ids = ids + Integer.toString(i.getId());
                }
                else {
                    ids = ids + Integer.toString(i.getId()) + ",";
                    j++;
                }
            }
            String insert_person = "INSERT INTO People VALUES ("+p.getPerson_id()+",'"+p.getName()+"','"
                    + p.getUID()+"','"+p.getAddress()+"','"+ids+"','"
                    +p.getStatement_ids().toString()+"','"+p.getTransaction_ids().toString()+"')";
            stmt.executeUpdate(insert_person);


        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void readPeople(){

        PersonService personService = PersonService.getInstance();
        AccountService accountService = AccountService.getInstance();
        DebitService debitService = DebitService.getInstance();
        SavingsService savingsService = SavingsService.getInstance();
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {
        String QUERY_PEOPLE = "SELECT * FROM People";
        ResultSet rs1 = stmt.executeQuery(QUERY_PEOPLE);
        while(rs1.next()) {
            List<Integer> accounts = new Vector<>();
            var ac = rs1.getString("account_ids").split(",");
            for (var i : ac) {
                accounts.add(Integer.parseInt(i));
            }


            List<Integer> transactions = new Vector<>();
            var tr = rs1.getString("transaction_history_ids").replace("[", "");
            tr = tr.replace("]", "");
            tr = tr.replace(" ", "");
            var trans = tr.split(",");
            for (var i : trans) {
                transactions.add(Integer.parseInt(i));
            }

            List<Integer> statements = new Vector<>();
            var st = rs1.getString("statement_history_ids").replace("[", "");
            st = st.replace("]", "");
            st = st.replace(" ", "");
            var stats = st.split(",");

            if(stats.length>0&& !Objects.equals(stats[0], "")) {
                for (var i : stats) {
                    statements.add(Integer.parseInt(i));
                }
            }

            List<Account> listaaccounts = new Vector<>();
            List<Transaction> listatrans = new Vector<>();
            List<com.company.entities.Statement> listastate = new Vector<>();
            Person alex2 = new Person(rs1.getString("name"), rs1.getString("uid"), rs1.getString("address"), rs1.getInt("person_id"),
                    listaaccounts, listastate, listatrans);


            for (var i : accounts)
                alex2.addAccountId(i);

            for (var i : statements)
                alex2.addStatementId(i);

            for (var i : transactions)
                alex2.addTransactionId(i);

            personService.addPerson(alex2);

        }
        rs1.close();
    }catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public void deleteAllPeople(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {
            String DELETE_ALL_PEOPLE = "DELETE FROM People";
            stmt.executeUpdate(DELETE_ALL_PEOPLE);

    }catch (SQLException e) {
        e.printStackTrace();
    }
    }

    public void deletePeopleTable(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String DROP_PEOPLE = "DROP TABLE People";
            stmt.executeUpdate(DROP_PEOPLE);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


}