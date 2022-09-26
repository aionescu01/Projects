package com.company.repos;

import com.company.entities.Account;
import com.company.entities.Debit;
import com.company.entities.Person;
import com.company.entities.Savings;
import com.company.services.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class StatementRepository {

    public void CreateTable() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao", "root", "root");
                Statement stmt = conn.createStatement();
        ) {

            String statements_table = "CREATE TABLE Statements " +
                    "(id INTEGER not NULL, " +
                    " account_id INTEGER, " +
                    " state VARCHAR(1000), " +
                    " PRIMARY KEY ( id )) ";
            stmt.executeUpdate(statements_table);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addStatement(com.company.entities.Statement s){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String insert_statement = "INSERT INTO Statements VALUES ("+s.getId()+ "," + s.getAccount_id()+",'"+ s.getStatement() + "')";
            stmt.executeUpdate(insert_statement);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateStatement(int id,com.company.entities.Statement s){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String update_statement = "UPDATE Statements SET "+ "account_id=" + s.getAccount_id()+",state='"+ s.getStatement()+"' WHERE id="+id;
            stmt.executeUpdate(update_statement);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void readStatements(){
        String QUERY_STATS = "SELECT * FROM Statements";

        AccountService accountService = AccountService.getInstance();
        StatementService statementService = StatementService.getInstance();
        PersonService personService = PersonService.getInstance();
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            ResultSet rs4 = stmt.executeQuery(QUERY_STATS);
            while (rs4.next()){
                var id = rs4.getInt("id");
                var account_id = rs4.getInt("account_id");
                var state = rs4.getString("state");
                Account a = accountService.getAccountById(account_id);
                com.company.entities.Statement statement = new com.company.entities.Statement(a);
                statementService.addStatement(statement);
                Person p = personService.getPersonById(a.getPerson_id());
                personService.AddStatement(p,statement);
            }
            rs4.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllStatements(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String DELETE_ALL_STATS = "DELETE FROM Statements";
            stmt.executeUpdate(DELETE_ALL_STATS);


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStatementsTable(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String DROP_STATS = "DROP TABLE Statements";
            stmt.executeUpdate(DROP_STATS);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
