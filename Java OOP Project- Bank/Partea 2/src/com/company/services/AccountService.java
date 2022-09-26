package com.company.services;

import com.company.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountService {
    private List<Account> Accounts = new ArrayList<>();
    private static AccountService instance;

    private AccountService(){}

    public static AccountService getInstance(){
        if(instance==null){
            instance=new AccountService();
        }
        return instance;
    }
    PersonService personService = PersonService.getInstance();

    public Statement doStatement(Account d) {
        System.out.println("-----------------STATEMENT-------------------");
        System.out.println(d.getBank() + " BANK");
        System.out.println("Account, id " + d.getId() + " of " +
                d.getName());
        System.out.println("Statement made on the date: " + LocalDate.now() + " " + LocalTime.now());
        System.out.println("Current balance = " + d.getBalance());
        System.out.println("Past transactions below: ");
        for (Transaction i : d.getTransaction_history())
            System.out.println(i.getTransaction());
        System.out.println("--------------END OF STATEMENT---------------");
        return new Statement(d);
    }

    public Transaction AddFunds(Account a, Transaction t, double sum, String Bank) {
        a.setBalance(a.getBalance()+sum);
        t.setSum(sum);
        t.setTransaction("Added the sum of " + sum + " to account on the date " + LocalDate.now() + " " + LocalTime.now() + " from bank " + Bank +
                ", account balance = " + a.getBalance());
        personService.AddTransaction(new Person(a.getName(), a.getUID(), a.getAddress(), a.getPerson_id(), a.getAccounts(), a.getStatements_history(), a.getTransaction_history()), t);
        return t;
    }

    public Transaction WithdrawFunds(Account d, Transaction t, double sum) {
        if (d.getBalance() - sum  >= 0) {
            d.setBalance(d.getBalance() - sum);
            t.setSum(sum);
            t.setTransaction("Withdrawn the sum of " + sum + " from account on the date " + LocalDate.now() + " " + LocalTime.now() +
                    ", account balance = " + d.getBalance());
            t.setDate(LocalDateTime.now());

            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), t);
            return t;
        } else {
            t.setSum(sum);
            t.setTransaction("FAILED: INSUFFICIENT FUNDS: Attempted to withdraw the sum of " + sum + " from account on the date " + LocalDate.now() + " " + LocalTime.now()  +
                    ", account balance = " + d.getBalance());
            t.setDate(LocalDateTime.now());
            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), t);
            return t;
        }
    }

    public List<Transaction> SendFunds(Account d, Account a, Transaction tsent, Transaction treceived, double sum) {

        if (d.getBalance() - sum >= 0) {
            d.setBalance(d.getBalance() - sum);
            a.setBalance(a.getBalance() + sum);
            tsent.setSum(sum);
            tsent.setTransaction("Sent the sum of " + sum + " from account " + d.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now());
            tsent.setDate(LocalDateTime.now());

            treceived.setSum(sum);
            treceived.setTransaction("Received the sum of " + sum + " from account " + d.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now() + " account balance = " + d.getBalance());
            treceived.setDate(LocalDateTime.now());

            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), tsent);
            personService.AddTransaction(new Person(a.getName(), a.getUID(), a.getAddress(), a.getPerson_id(), a.getAccounts(), a.getStatements_history(), a.getTransaction_history()), treceived);

            List<Transaction> returnlist = new ArrayList<>();
            returnlist.add(tsent);
            returnlist.add(treceived);
            return returnlist;
        } else {
            tsent.setSum(sum);
            tsent.setTransaction("FAILED: INSUFFICIENT FUNDS: Attempted to send the sum of " + sum + " from account " + d.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now()+ " account balance = " + d.getBalance());
            tsent.setDate(LocalDateTime.now());
            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), tsent);
            List<Transaction> returnlist = new ArrayList<>();
            returnlist.add(tsent);
            return returnlist;
        }
    }


    public List<Account> getAccounts(){
        return new ArrayList<>(this.Accounts);
    }

    public Account getAccountById(int id){
        Account ac = new Account();
        for(Account p : this.Accounts)
            if(p.getId()==id)
                ac=p;
        return ac;
    }

    public void updateAccount(int id, Account Account){
        for(Account p : this.Accounts)
            if(p.getId()==id)
            {
                this.Accounts.remove(p);
                Account.setId(id);
                this.Accounts.add(Account);
                return;
            }
    }

    public void addAccount(Account Account){
        this.Accounts.add(Account);
    }

    public void deleteAccountById(int id){
        for(Account p : this.Accounts)
            if(p.getId()==id)
            {
                this.Accounts.remove(p);
                return;
            }
    }

    public void deleteAccounts(){
        this.Accounts.clear();
    }

    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))) {

            String line;
            var headerLine = in.readLine();
            while((line = in.readLine()) != null ) {
                //String[] fields = line.replaceAll(" ", "").split(",");
                String[] fields = line.replaceAll("\"", "").split(";");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("No saved accounts!");
        }

        return columns;
    }

    public void loadFromCSV() {
        PersonService personService = PersonService.getInstance();
        try{
            var columns = AccountService.getCSVColumns("src/com/company/data/accounts.csv");
        for(var fields : columns){

            Person p = personService.getPersonById(Integer.parseInt(fields[0]));

            LocalDate data = LocalDate.parse(fields[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            var newAccount = new Account(
                    p,
                    Double.parseDouble(fields[1]),
                    data
            );
            Accounts.add(newAccount);
            personService.AddAccount(p,newAccount);

        }
        }catch (DateTimeParseException e){
            System.out.println(e.toString());
        }
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("src/com/company/data/accounts.csv");
            for(var customer : this.Accounts){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

}
