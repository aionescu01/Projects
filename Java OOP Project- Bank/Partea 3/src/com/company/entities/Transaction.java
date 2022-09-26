package com.company.entities;

import com.company.services.AccountService;
import com.company.services.PersonService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;

public class Transaction {
    private Account account;
    private Debit debit = new Debit();
    private Savings savings = new Savings();
    private String transaction="";
    private double sum;
    private LocalDateTime date;
    private int destination_id;//contul destinatie
    private int id;
    private static int number_of_transactions=1;

    PersonService personService = PersonService.getInstance();
    AccountService accountService = AccountService.getInstance();

    public Transaction(){}

    public Transaction(Account acc){
        this.account=acc;
        this.id=number_of_transactions;
        this.date = LocalDateTime.now();
        //acc.addTransactionId(id);
        //System.out.println(acc.getTransaction_ids());
        number_of_transactions++;
    }



    public Transaction(Account acc,double sum, String transaction, LocalDateTime date){
        this.account=acc;
        this.sum=sum;
        this.transaction=transaction;
        this.date=date;
        this.id=number_of_transactions;
        //acc.addTransactionId(id);
        //System.out.println(id);
        number_of_transactions++;
    }

    public int getAccountId(){
        return account.getId();
    }

    public String getTransaction() {
        return transaction;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transaction='" + transaction + '\'' +
                ", sum=" + sum +
                ", date=" +date+
                //", destination_id=" + destination_id +
                '}';
    }

    public String toCSV() {
        var x = account.getId();
        return  Integer.toString(x) +
                "," + transaction +
                "," + sum+
                "," + date
                //+ "," + destination_id
        ;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getNumber_of_transactions() {
        return number_of_transactions;
    }

    public static void setNumber_of_transactions(int number_of_transactions) {
        Transaction.number_of_transactions = number_of_transactions;
    }

}