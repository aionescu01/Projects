package com.company.repos;

import com.company.entities.*;
import com.company.services.AccountService;
import com.company.services.DebitService;
import com.company.services.PersonService;
import com.company.services.SavingsService;

import java.sql.*;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class AccountRepository {

    public void CreateTable() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao", "root", "root");
                Statement stmt = conn.createStatement();
        ) {

            String account_table = "CREATE TABLE Accounts " +
                    "(id INTEGER not NULL, " +
                    " type VARCHAR(255), " +
                    " person_id INTEGER, " +
                    " balance FLOAT, " +
                    " opening_date VARCHAR(255), " +
                    " debit_type VARCHAR(255), " +
                    " period INTEGER , " +
                    " interest_rate FLOAT, " +
                    " balance_history VARCHAR(1000), " +
                    " PRIMARY KEY ( id ), "+
                    "FOREIGN KEY (person_id) REFERENCES People(person_id))";
            stmt.executeUpdate(account_table);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addAccount(Account a){
        try(
                Connection conn=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao","root","root");
                Statement stmt = conn.createStatement();
        ) {

            String insert_account = "INSERT INTO Accounts VALUES ("+ a.getId()+ ",'"+a.getClass().getSimpleName()+"',"+a.getPerson_id()+","+ a.getBalance() +",'"+a.getOpening_date()+
                    "','',0,0.0,'')";
            stmt.executeUpdate(insert_account);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void addDebit(Debit a){
        try(
                Connection conn=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao","root","root");
                Statement stmt = conn.createStatement();
        ) {

            String insert_debit ="INSERT INTO Accounts VALUES ("+ a.getId()+ ",'"+a.getClass().getSimpleName()+"',"+a.getPerson_id()+","+ a.getBalance() +",'"+a.getOpening_date()+
                    "','"+ a.getAccount_type() + "', 0,0.0,'')";
            stmt.executeUpdate(insert_debit);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void addSavings(Savings a){
        try(
                Connection conn=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao","root","root");
                Statement stmt = conn.createStatement();
        ) {

            String insert_savings ="INSERT INTO Accounts VALUES ("+ a.getId()+ ",'"+a.getClass().getSimpleName()+"',"+a.getPerson_id()+","+ a.getBalance() +",'"+a.getOpening_date()+
                    "','',"+ a.getPeriod() + ","+ a.getInterest_rate()+",'"+ Arrays.toString(a.getBalance_history()) +"')";
            stmt.executeUpdate(insert_savings);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void readAccounts(){
        String QUERY_ACCTS = "SELECT * FROM Accounts";
        PersonService personService = PersonService.getInstance();
        AccountService accountService = AccountService.getInstance();
        DebitService debitService = DebitService.getInstance();
        SavingsService savingsService = SavingsService.getInstance();
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            ResultSet rs2 = stmt.executeQuery(QUERY_ACCTS);
            while (rs2.next()){
                var id = rs2.getInt("id");
                var type = rs2.getString("type");
                var person_id = rs2.getInt("person_id");
                var balance = rs2.getDouble("balance");
                var date = rs2.getString("opening_date");
                Person p = personService.getPersonById(person_id);
                LocalDate opening_date = LocalDate.parse(date);
                if(Objects.equals(type, "Savings")){
                    Savings s = new Savings(p,balance,opening_date,rs2.getInt("period"),rs2.getDouble("interest_rate"));
                    s.setId(id);
                    savingsService.addSavings(s);
                    accountService.addAccount(s);
                    personService.AddAccount(p,s);
                }
                else if(Objects.equals(type, "Debit")){
                    Debit d = new Debit(p,balance,opening_date,rs2.getString("debit_type"));
                    d.setId(id);
                    debitService.addDebit(d);
                    accountService.addAccount(d);
                    personService.AddAccount(p,d);
                }
                else {
                    Account a = new Account(p,balance,opening_date);
                    a.setId(id);
                    accountService.addAccount(a);
                    personService.AddAccount(p,a);
                }
            }
            rs2.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllAccounts(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String DELETE_ALL_ACCTS = "DELETE FROM Accounts";
            stmt.executeUpdate(DELETE_ALL_ACCTS);


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccountsTable(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String DROP_ACCTS = "DROP TABLE Accounts";
            stmt.executeUpdate(DROP_ACCTS);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
