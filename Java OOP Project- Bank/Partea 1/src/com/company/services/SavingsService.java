package com.company.services;

import com.company.entities.*;
import com.company.entities.Savings;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class SavingsService {

    private List<Savings> Savings_accts = new ArrayList<>();
    private static SavingsService instance;

    private SavingsService(){}

    public static SavingsService getInstance(){
        if(instance==null){
            instance=new SavingsService();
        }
        return instance;
    }

    PersonService personService = PersonService.getInstance();
    AccountService accountService = AccountService.getInstance();

    public Statement doStatement(Savings d) {
        System.out.println("-----------------STATEMENT-------------------");
        System.out.println(d.getBank() + " BANK");
        System.out.println("Savings account, id " + d.getId() + " of " +
                d.getName());
        System.out.println("Statement made on the date: " + LocalDate.now() + " " + LocalTime.now());
        System.out.println("Current balance = " + d.getBalance());
        System.out.println("Past transactions below: ");
        for (Transaction i : d.getTransaction_history())
            System.out.println(i.getTransaction());
        System.out.println("--------------END OF STATEMENT---------------");
        return new Statement(d);
    }

    public Transaction AddFunds(Savings s, Transaction t, double sum) {
        if (s.getOpening_date().compareTo(LocalDate.now().minusMonths(s.getPeriod())) < 0) {
            s.setBalance(s.getBalance() + sum);
            t.setSum(sum);
            t.setTransaction("Added the sum of " + sum + " to savings account on the date " + LocalDate.now() + " " + LocalTime.now() +
                    ", account balance = " + s.getBalance());
            t.setDate(LocalDateTime.now());

            personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), t);
            return t;
        } else {
            System.out.println();
            t.setSum(sum);
            t.setTransaction("FAILED: SAVINGS ACCOUNT LOCKED UNTIL" + s.getOpening_date().plusMonths(s.getPeriod()) + ": Attempted to add the sum of " + sum
                    + " to savings account on the date " + LocalDate.now() + " " + LocalTime.now() + ", account balance = " + s.getBalance());
            t.setDate(LocalDateTime.now());
            personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), t);
            return t;
        }
    }

    public Transaction WithdrawFunds(Savings s, Transaction t, double sum) {
        if (s.getOpening_date().compareTo(LocalDate.now().minusMonths(s.getPeriod())) < 0) {
            if (s.getBalance() - sum >= 0) {
                s.setBalance(s.getBalance() - sum);
                t.setSum(sum);
                t.setTransaction("Withdrawn the sum of " + sum + " from debit account on the date " + LocalDate.now() + " " + LocalTime.now() +
                        ", account balance = " + s.getBalance());
                t.setDate(LocalDateTime.now());

                personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), t);
                return t;
            } else {
                t.setSum(sum);
                t.setTransaction("FAILED: INSUFFICIENT FUNDS: Attempted to withdraw the sum of " + sum + " from debit account on the date " + LocalDate.now() + " " + LocalTime.now() +
                        ", account balance = " + s.getBalance());
                t.setDate(LocalDateTime.now());
                personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), t);
                return t;
            }
        }
        else {
            System.out.println();
            t.setSum(sum);
            t.setTransaction("FAILED: SAVINGS ACCOUNT LOCKED UNTIL" + s.getOpening_date().plusMonths(s.getPeriod()) + ": Attempted to withdraw the sum of " + sum
                    + " from savings account on the date " + LocalDate.now() + " " + LocalTime.now() + ", account balance = " + s.getBalance());
            t.setDate(LocalDateTime.now());
            personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), t);
            return t;
        }
    }


    public List<Transaction> SendFunds(Savings s, Account a, Transaction tsent, Transaction treceived, double sum) {
        if (s.getOpening_date().compareTo(LocalDate.now().minusMonths(s.getPeriod())) < 0) {

            if (s.getBalance() - sum >= 0) {
                s.setBalance(s.getBalance() - sum);
                a.setBalance(a.getBalance() + sum);
                tsent.setSum(sum);
                tsent.setTransaction("Sent the sum of " + sum + " from debit account " + s.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now());
                tsent.setDate(LocalDateTime.now());

                treceived.setSum(sum);
                treceived.setTransaction("Received the sum of " + sum + " from debit account " + s.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now() + " account balance = " + s.getBalance());
                treceived.setDate(LocalDateTime.now());

                personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), tsent);
                personService.AddTransaction(new Person(a.getName(), a.getUID(), a.getAddress(), a.getPerson_id(), a.getAccounts(), a.getStatements_history(), a.getTransaction_history()), treceived);

                List<Transaction> returnlist = new ArrayList<>();
                returnlist.add(tsent);
                returnlist.add(treceived);
                return returnlist;
            } else {
                tsent.setSum(sum);
                tsent.setTransaction("FAILED: INSUFFICIENT FUNDS: Attempted to send the sum of " + sum + " from savings account " + s.getId() + " to account " + a.getId() + " on the date " + LocalDate.now() + " " + LocalTime.now()+ " account balance = " + s.getBalance());
                tsent.setDate(LocalDateTime.now());
                personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), tsent);
                List<Transaction> returnlist = new ArrayList<>();
                returnlist.add(tsent);
                return returnlist;
            }
        }
        else {
            tsent.setSum(sum);
            tsent.setTransaction("FAILED: SAVINGS ACCOUNT LOCKED UNTIL" + s.getOpening_date().plusMonths(s.getPeriod()) + ": Attempted to withdraw the sum of " + sum
                    + " from savings account on the date " + LocalDate.now() + " " + LocalTime.now() + ", account balance = " + s.getBalance());
            tsent.setDate(LocalDateTime.now());
            personService.AddTransaction(new Person(s.getName(), s.getUID(), s.getAddress(), s.getPerson_id(), s.getAccounts(), s.getStatements_history(), s.getTransaction_history()), tsent);
            List<Transaction> returnlist = new ArrayList<>();
            returnlist.add(tsent);
            return returnlist;
        }
    }



    public List<Savings> getSavings_accts(){
        return new ArrayList<>(this.Savings_accts);
    }

    public Savings getSavingsById(int id){
        Savings sv = new Savings();
        for(Savings p : this.Savings_accts)
            if(p.getId()==id)
                sv=p;
        return sv;
    }

    public void updateSavings(int id, Savings Savings){
        for(Savings p : this.Savings_accts)
            if(p.getId()==id)
            {
                this.Savings_accts.remove(p);
                Savings.setId(id);
                this.Savings_accts.add(Savings);
                return;
            }
    }

    public void addSavings(Savings Savings){
        this.Savings_accts.add(Savings);
    }

    public void deleteSavingsById(int id){
        for(Savings p : this.Savings_accts)
            if(p.getId()==id)
            {
                this.Savings_accts.remove(p);
                return;
            }
    }

    public void deleteSavings_accts(){
        this.Savings_accts.clear();
    }
    
}
