package com.company.services;

import com.company.entities.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DebitService {
    private List<Debit> debit_accts = new ArrayList<>();
    private static DebitService instance;

    private DebitService() {
    }

    public static DebitService getInstance() {
        if (instance == null) {
            instance = new DebitService();
        }
        return instance;
    }

    PersonService personService = PersonService.getInstance();
    AccountService accountService = AccountService.getInstance();

    public Statement doStatement(Debit d) {
        System.out.println("-----------------STATEMENT-------------------");
        System.out.println(d.getBank() + " BANK");
        System.out.println("Debit account, id " + d.getId() + " of " +
                d.getName());
        System.out.println("Statement made on the date: " + LocalDate.now() + " " + LocalTime.now());
        System.out.println("Current balance = " + d.getBalance());
        System.out.println("Past transactions below: ");
        for (Transaction i : d.getTransaction_history())
            System.out.println(i.getTransaction());
        System.out.println("--------------END OF STATEMENT---------------");
        return new Statement(d);
    }

    public Transaction AddFunds(Debit d, Transaction t, double sum, String Bank) {
        double fee;
        if (!Objects.equals(Bank, d.getBank()))
            if (Objects.equals(d.getAccount_type(), "Gold"))
                fee = 2;
            else fee = 5;
        else fee = 0;
        d.setBalance(d.getBalance() + sum - fee);
        t.setSum(sum);
        t.setTransaction("Added the sum of " + sum + " to debit account on the date " + LocalDate.now() + " " + LocalTime.now() + " from bank " + Bank + " with fee " + fee +
                ", account balance = " + d.getBalance());
        t.setDate(LocalDateTime.now());

        personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), t);
        return t;
    }

    public Transaction WithdrawFunds(Debit d, Transaction t, double sum, String Bank) {
        double fee;
        if (!Objects.equals(Bank, d.getBank()))
            if (Objects.equals(d.getAccount_type(), "Gold"))
                fee = 2;
            else fee = 5;
        else fee = 0;
        if (d.getBalance() - sum - fee >= 0) {
            d.setBalance(d.getBalance() - sum - fee);
            t.setSum(sum);
            t.setTransaction("Withdrawn the sum of " + sum + " from debit account on the date " + LocalDate.now() + " " + LocalTime.now() + " from bank " + Bank + " with fee " + fee +
                    ", account balance = " + d.getBalance());
            t.setDate(LocalDateTime.now());

            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), t);
            return t;
        } else {
            t.setSum(sum);
            t.setTransaction("FAILED: INSUFFICIENT FUNDS: Attempted to withdraw the sum of " + sum + " from debit account on the date " + LocalDate.now() + " " + LocalTime.now() + " from bank " + Bank + " with fee " + fee +
                    ", account balance = " + d.getBalance());
            t.setDate(LocalDateTime.now());
            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), t);
            return t;
        }
    }

    public List<Transaction> SendFunds(Debit d, Account a, Transaction tsent, Transaction treceived, double sum) {

        if (d.getBalance() - sum >= 0) {
            d.setBalance(d.getBalance() - sum);
            a.setBalance(a.getBalance() + sum);
            tsent.setSum(sum);
            tsent.setTransaction("Sent the sum of " + sum + " from debit account " + d.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now());
            tsent.setDate(LocalDateTime.now());

            treceived.setSum(sum);
            treceived.setTransaction("Received the sum of " + sum + " from debit account " + d.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now() + " account balance = " + d.getBalance());
            treceived.setDate(LocalDateTime.now());

            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), tsent);
            personService.AddTransaction(new Person(a.getName(), a.getUID(), a.getAddress(), a.getPerson_id(), a.getAccounts(), a.getStatements_history(), a.getTransaction_history()), treceived);

            List<Transaction> returnlist = new ArrayList<>();
            returnlist.add(tsent);
            returnlist.add(treceived);
            return returnlist;
        } else {
            tsent.setSum(sum);
            tsent.setTransaction("FAILED: INSUFFICIENT FUNDS: Attempted to send the sum of " + sum + " from debit account " + d.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now()+ " account balance = " + d.getBalance());
            tsent.setDate(LocalDateTime.now());
            personService.AddTransaction(new Person(d.getName(), d.getUID(), d.getAddress(), d.getPerson_id(), d.getAccounts(), d.getStatements_history(), d.getTransaction_history()), tsent);
            List<Transaction> returnlist = new ArrayList<>();
            returnlist.add(tsent);
            return returnlist;
        }
    }

    public List<Debit> getdebit_accts() {
        return new ArrayList<>(this.debit_accts);
    }

    public Debit getDebitById(int id) {
        Debit db = new Debit();
        for (Debit p : this.debit_accts)
            if (p.getId() == id)
                db = p;
        return db;
    }

    public void updateDebit(int id, Debit Debit) {
        for (Debit p : this.debit_accts)
            if (p.getId() == id) {
                this.debit_accts.remove(p);
                Debit.setId(id);
                this.debit_accts.add(Debit);
                return;
            }
    }

    public void addDebit(Debit Debit) {
        this.debit_accts.add(Debit);
    }

    public void deleteDebitById(int id) {
        for (Debit p : this.debit_accts)
            if (p.getId() == id) {
                this.debit_accts.remove(p);
                return;
            }
    }

    public void deletedebit_accts() {
        this.debit_accts.clear();
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
        AccountService accountService = AccountService.getInstance();
        try{
            var columns = DebitService.getCSVColumns("src/com/company/data/debit_accounts.csv");
            for(var fields : columns){

                Account a = accountService.getAccountById(Integer.parseInt(fields[0]));

                var newAccount = new Debit(
                        a,
                        fields[1]
                );
                debit_accts.add(newAccount);
                personService.AddAccount(a,newAccount);

            }
        }catch (DateTimeParseException e){
            System.out.println(e.toString());
        }
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("src/com/company/data/debit_accounts.csv");
            for(var customer : this.debit_accts){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

}
