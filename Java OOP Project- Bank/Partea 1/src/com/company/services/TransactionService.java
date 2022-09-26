package com.company.services;

import com.company.entities.Transaction;

import java.util.ArrayList;
import java.util.List;


public class TransactionService {
    private List<Transaction> transactions = new ArrayList<>();
    private static TransactionService instance;

    private TransactionService(){}

    public static TransactionService getInstance(){
        if(instance==null){
            instance=new TransactionService();
        }
        return instance;
    }

    public List<Transaction> getTransactions(){
        return new ArrayList<>(this.transactions);
    }

    public Transaction getTransactionById(int id){
        Transaction tr = new Transaction();
        for(Transaction p : this.transactions)
            if(p.getId()==id)
                tr=p;
        return tr;
    }

    public void updateTransaction(int id, Transaction Transaction){
        for(Transaction p : this.transactions)
            if(p.getId()==id)
            {
                this.transactions.remove(p);
                Transaction.setId(id);
                this.transactions.add(Transaction);
                return;
            }
    }

    public void addTransaction(Transaction Transaction){
        this.transactions.add(Transaction);
    }

    public void deleteTransactionById(int id){
        for(Transaction p : this.transactions)
            if(p.getId()==id)
            {
                this.transactions.remove(p);
                return;
            }
    }

    public void deleteTransactions(){
        this.transactions.clear();
    }
}
