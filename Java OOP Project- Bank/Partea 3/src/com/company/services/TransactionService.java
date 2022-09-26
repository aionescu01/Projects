package com.company.services;

import com.company.entities.Account;
import com.company.entities.Statement;
import com.company.entities.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))) {

            String line;
            var headerLine = in.readLine();
            while((line = in.readLine()) != null ) {
                //String[] fields = line.replaceAll(" ", "").split(",");
                String[] fields = line.replaceAll("T", " ").split(";");
                //fields = line.replaceAll("\"", " ").split(";");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("No saved statements!");
        }

        return columns;
    }

    public void loadFromCSV() {
        AccountService accountService = AccountService.getInstance();
        PersonService personService = PersonService.getInstance();
        try{
            var columns = TransactionService.getCSVColumns("src/com/company/data/transactions.csv");
            for(var fields : columns){

                Account a = accountService.getAccountById(Integer.parseInt(fields[0]));
                LocalDateTime data = LocalDateTime.parse(fields[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n"));
                //System.out.println(fields[2]);
                var newAccount = new Transaction(
                        a,
                        Double.parseDouble(fields[2]),
                        fields[1],
                        data
                );
                newAccount.setTransaction(fields[1]);
                transactions.add(newAccount);
                personService.AddTransaction(a,newAccount);

            }
        }catch (DateTimeParseException e){
            System.out.println(e.toString());
        }
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("src/com/company/data/transactions.csv");
            for(var customer : this.transactions){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
