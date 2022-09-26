package com.company.services;

import com.company.entities.*;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class ReadService {

    private static ReadService instance;
    private ReadService(){}

    public static ReadService getInstance(){
        if(instance==null){
            instance=new ReadService();
        }
        return instance;
    }

    //static final String DB_URL = "jdbc:sqlserver://localhost\\DBTEST:1433;database=dbpao;integratedSecurity=true;trustServerCertificate=true;authenticationScheme=NativeAuthentication;";
    static final String QUERY_PEOPLE = "SELECT * FROM People";
    static final String QUERY_ACCTS = "SELECT * FROM Accounts";
    static final String QUERY_TRANS = "SELECT * FROM Transactions";
    static final String QUERY_STATS = "SELECT * FROM Statements";
    static final String DELETE_ALL_STATEMENTS = "DELETE FROM Statements";
    static final String DELETE_ALL_TRANS = "DELETE FROM Transactions";
    static final String DELETE_ALL_PEOPLE = "DELETE FROM People";
    static final String DELETE_ALL_ACCTS = "DELETE FROM Accounts";

    PersonService personService = PersonService.getInstance();
    AccountService accountService = AccountService.getInstance();
    TransactionService transactionService = TransactionService.getInstance();
    StatementService statementService = StatementService.getInstance();
    DebitService debitService = DebitService.getInstance();
    SavingsService savingsService = SavingsService.getInstance();


    public void CreateTables(){
        try(
                //Connection conn = DriverManager.getConnection(DB_URL);
                Connection conn=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao","root","root");
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



        String transaction_table = "CREATE TABLE Transactions " +
                "(id INTEGER not NULL, " +
                " account_id INTEGER, " +
                " sum FLOAT, " +
                " trans VARCHAR(1000), " +
                " date VARCHAR(255), " +
                " PRIMARY KEY ( id )) ";
        stmt.executeUpdate(transaction_table);



        String statements_table = "CREATE TABLE Statements " +
                "(id INTEGER not NULL, " +
                " account_id INTEGER, " +
                " state VARCHAR(1000), " +
                " PRIMARY KEY ( id )) ";
        stmt.executeUpdate(statements_table);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ReadTables(){
        try(
                Connection conn=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao","root","root");
                //Connection conn = DriverManager.getConnection(DB_URL);
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
                    personService.AddAccount(p,s);
                }
                else if(Objects.equals(type, "Debit")){
                    Debit d = new Debit(p,balance,opening_date,rs2.getString("debit_type"));
                    d.setId(id);
                    debitService.addDebit(d);
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
                transactionService.addTransaction(tr);
            }
            rs3.close();


            ResultSet rs4 = stmt.executeQuery(QUERY_STATS);
            while (rs4.next()){
                var id = rs4.getInt("id");
                var account_id = rs4.getInt("account_id");
                var state = rs4.getString("state");
                Account a = accountService.getAccountById(account_id);
                com.company.entities.Statement statement = new com.company.entities.Statement(a);
            }
            rs4.close();

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
                for (var i : stats) {
                    statements.add(Integer.parseInt(i));
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

    public void addPerson(Person p){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
                //Connection conn = DriverManager.getConnection(DB_URL);
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

    public void addAccount(Account a){
        try(
                Connection conn=DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbpao","root","root");
                //Connection conn = DriverManager.getConnection(DB_URL);
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
                //Connection conn = DriverManager.getConnection(DB_URL);
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
                //Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {

            String insert_savings ="INSERT INTO Accounts VALUES ("+ a.getId()+ ",'"+a.getClass().getSimpleName()+"',"+a.getPerson_id()+","+ a.getBalance() +",'"+a.getOpening_date()+
                    "','',"+ a.getPeriod() + ","+ a.getInterest_rate()+",'"+ Arrays.toString(a.getBalance_history()) +"')";
            stmt.executeUpdate(insert_savings);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void addStatement(com.company.entities.Statement s){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            //Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {

            String insert_statement = "INSERT INTO Statements VALUES ("+s.getId()+ "," + s.getAccount_id()+",'"+ s.getStatement() + "')";
            stmt.executeUpdate(insert_statement);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addTransaction(Transaction t){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            //Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {

            String insert_tranz = "INSERT INTO Transactions VALUES ("+ t.getId()+ ","+t.getAccountId()+","+t.getSum()+",'"+ t.getTransaction() +"','"+t.getDate()+ "')";
            stmt.executeUpdate(insert_tranz);

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteAll(){
        try(Connection conn=DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbpao","root","root");
            //Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(DELETE_ALL_STATEMENTS);
            stmt.executeUpdate(DELETE_ALL_ACCTS);
            stmt.executeUpdate(DELETE_ALL_PEOPLE);
            stmt.executeUpdate(DELETE_ALL_TRANS);

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
