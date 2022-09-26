package com.company.services;

import com.company.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PersonService {
    private List<Person> people = new ArrayList<>();
    private static PersonService instance;
    public Sortbydate date = new Sortbydate();

    private PersonService(){}

    public static PersonService getInstance(){
        if(instance==null){
            instance=new PersonService();
        }
        return instance;
    }

    public void AddAccount(Person p, Account a){
        List<Account> accts= p.getAccounts();
        accts.add(a);
        accts.sort(new Sortbyperiod());
        p.setAccounts(accts);
        //savings accounts get sorted by shortest period
    }

    public void AddStatement(Person p, Statement s){
        List<Statement> statements= p.getStatements_history();
        statements.add(s);
        p.addStatementId(s.getId());
        p.setStatements_history(statements);
    }

    public void AddTransaction(Person p, Transaction t)
    {
        List<Transaction> transactions= p.getTransaction_history();
        p.addTransactionId(t.getId());
        transactions.add(t);
        //transactions.sort( date);
        p.setTransaction_history(transactions);
    }

    public List<Person> getPeople(){
        return new ArrayList<>(this.people);
    }

    public Person getPersonById(int id){
        Person person = new Person();
        for(Person p : this.people)
            if(p.getPerson_id()==id)
                person=p;
        return person;
    }

    public void updatePerson(int id, Person person){
        for(Person p : this.people)
            if(p.getPerson_id()==id)
            {
                this.people.remove(p);
                person.setPerson_id(id);
                this.people.add(person);
                return;
            }
    }

    public void addPerson(Person person){

//        AccountService accountService = AccountService.getInstance();
//        List<Account> acc_history = new Vector<>();
//        for(var i:person.getAccount_ids())
//            acc_history.add(accountService.getAccountById(i));
//            person.setAccounts(acc_history);
//
//        TransactionService transactionService = TransactionService.getInstance();
//        List<Transaction> trans_history = new Vector<>();
//        for(var i:person.getTransaction_ids())
//            trans_history.add(transactionService.getTransactionById(i));
//            person.setTransaction_history(trans_history);
//
//        StatementService statementService = StatementService.getInstance();
//        List<Statement> statement_history = new Vector<>();
//        for(var i:person.getTransaction_ids())
//            statement_history.add(statementService.getStatementById(i));
//            person.setStatements_history(statement_history);

        this.people.add(person);
    }

    public void deletePersonById(int id){
        for(Person p : this.people)
            if(p.getPerson_id()==id)
            {
                this.people.remove(p);
                return;
            }
    }

    public void deletePeople(){
        this.people.clear();
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
            System.out.println("No saved people!");
        }

        return columns;
    }

    public void loadFromCSV() {
        var columns = PersonService.getCSVColumns("src/com/company/data/people.csv");
        for(var fields : columns){
            int x = 0;
            if(fields.length==4) {
                for (var pers : people) {
                    if (pers.getPerson_id() == Integer.parseInt(fields[3])) {
                        x = 1;
                        pers.setName(fields[0]);
                        pers.setUID(fields[1]);
                        pers.setAddress(fields[2]);
                    }
                }
            }
            if(x==0)
            {
                var newCustomer = new Person(
                    fields[0],
                    fields[1],
                    fields[2]
            );
                people.add(newCustomer);
            }
            //System.out.println(newCustomer);

        }
        //CustomerFactory.incrementUniqueId(columns.size());

    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("src/com/company/data/people.csv");
            for(var customer : this.people){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }

}
