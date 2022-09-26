package com.company.repos;

import com.company.entities.*;
import com.company.services.*;

import java.sql.*;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionRepository {

    public void CreateTable() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao", "root", "root");
                Statement stmt = conn.createStatement();
        ) {

            String transaction_table = "CREATE TABLE Transactions " +
                    "(id INTEGER not NULL, " +
                    " account_id INTEGER, " +
                    " sum FLOAT, " +
                    " trans VARCHAR(1000), " +
                    " date VARCHAR(255), " +
                    " PRIMARY KEY ( id )) ";
            stmt.executeUpdate(transaction_table);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addTransaction(Transaction t){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String insert_tranz = "INSERT INTO Transactions VALUES ("+ t.getId()+ ","+t.getAccountId()+","+t.getSum()+",'"+ t.getTransaction() +"','"+t.getDate()+ "')";
            stmt.executeUpdate(insert_tranz);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateTransaction(int id,Transaction t){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String update_transaction = "UPDATE Transactions SET "+ "account_id=" + t.getAccountId()+",sum="+ t.getSum()+ ",trans='"+t.getTransaction()+
                    "',date='"+t.getDate()+"' WHERE id="+id;
            stmt.executeUpdate(update_transaction);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void readTransactions(){
        String QUERY_TRANS = "SELECT * FROM Transactions";
        TransactionService transactionService = TransactionService.getInstance();
        PersonService personService = PersonService.getInstance();
        AccountService accountService = AccountService.getInstance();
        DebitService debitService = DebitService.getInstance();
        SavingsService savingsService = SavingsService.getInstance();
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            ResultSet rs3 = stmt.executeQuery(QUERY_TRANS);
            while (rs3.next()){
                var id = rs3.getInt("id");
                var account_id = rs3.getInt("account_id");
                var sum = rs3.getDouble("sum");
                var trans = rs3.getString("trans");
                var date = rs3.getString("date");
                LocalDateTime data = LocalDateTime.parse(date);
                Account a = accountService.getAccountById(account_id);

                Transaction tr = new Transaction(a,sum,trans,data);
                tr.setId(id);
                Person p = personService.getPersonById(a.getPerson_id());
                personService.AddTransaction(p,tr);

                transactionService.addTransaction(tr);
            }
            rs3.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllTransactions(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String DELETE_ALL_TRANS = "DELETE FROM Transactions";
            stmt.executeUpdate(DELETE_ALL_TRANS);


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransactionsTable(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            Statement stmt = conn.createStatement();
        ) {

            String DROP_TRANS = "DROP TABLE Transactions";
            stmt.executeUpdate(DROP_TRANS);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
