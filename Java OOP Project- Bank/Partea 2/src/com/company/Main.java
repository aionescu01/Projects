package com.company;

import com.company.entities.*;
import com.company.services.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.*;
public class Main {

    public static void main(String[] args) {


        Person alex = new Person("Aaa","1234","Aleea Fizicienilor 14");
        Account a1 = new Account(alex,50, LocalDate.now());
        Account a2 = new Account(alex,50, LocalDate.now());
        Account a3 = new Account(alex,50, LocalDate.now());
        //System.out.println(a2);
        //System.out.println(alex.getPerson_id());

        AccountService accountService = AccountService.getInstance();
        PersonService personService = PersonService.getInstance();

//        personService.addPerson(alex);
//        accountService.addAccount(a2);

//        personService.loadFromCSV();
//        accountService.loadFromCSV();
//
//        System.out.println(accountService.getAccounts());
//        System.out.println(personService.getPeople());

        personService.loadFromCSV();
        System.out.println(personService.getPeople());

//        personService.dumpToCSV();
//        accountService.dumpToCSV();
//
//        Transaction t = new Transaction(a2,0,null, LocalDateTime.now());
//        accountService.AddFunds(a2,t,10,"ING");
//        Statement s = new Statement(a2);
//        personService.addPerson(alex);
//        accountService.addAccount(a2);
//        accountService.addAccount(a1);
//        accountService.addAccount(a3);
//        //System.out.println(t);
//        //System.out.println(s);
//
////        System.out.println(accountService.getAccounts());
////        System.out.println(personService.getPeople());
////        Card c = new Card(alex,a2);
////        CardService cardService = CardService.getInstance();
////        cardService.addCard(c);
////        cardService.dumpToCSV();
//       // cardService.loadFromCSV();
//       // System.out.println(cardService.getCards());
////
//        DebitService debitService = DebitService.getInstance();
////        debitService.loadFromCSV();
//
//        SavingsService savingsService = SavingsService.getInstance();
//        savingsService.loadFromCSV();

//        Savings sav = new Savings(a2,6,1.2);
//        savingsService.addSavings(sav);
//
//        Debit deb = new Debit(a1,"Gold");
//        debitService.addDebit(deb);

//        debitService.dumpToCSV();
//        accountService.dumpToCSV();
//        savingsService.dumpToCSV();

//        System.out.println(savingsService.getSavings_accts());
//        System.out.println(debitService.getdebit_accts());
//        System.out.println(accountService.getAccounts());

//        StatementService statementService = StatementService.getInstance();
//        statementService.addStatement(s);
//        statementService.dumpToCSV();
//        //System.out.println(s.toCSV());
//        statementService.loadFromCSV();
//        System.out.println(statementService.getstatements());

//        TransactionService transactionService = TransactionService.getInstance();
//        transactionService.loadFromCSV();
//        System.out.println(transactionService.getTransactions());

//        personService.addPerson(alex);
//        User u = new User(alex,"aionescu01","1234","Admin");
//        UserService userService = UserService.getInstance();
//        //userService.addUser(u);
////        userService.dumpToCSV();
//        userService.loadFromCSV();
//        System.out.println(userService.getUsers());

//        transactionService.addTransaction(t);
//        transactionService.dumpToCSV();

//        List<String[]> columns = new ArrayList<>();
//
//        try(var in = new BufferedReader(new FileReader("src/com/company/test.csv"))) {
//            String line;
//
//            while((line = in.readLine()) != null ) {
//
//                String[] fields = line.replaceAll("\"", "").split(",");
//                //String[] fields = line.split(",");
//                columns.add(fields);
//            }
//        }
//        catch (IOException e) {
//            System.out.println("No saved customers!");
//        }
//
//        List<Person> customers = new ArrayList<Person>();
//
//        for(var fields : columns){
//            var newCustomer = new Person(
//                    fields[0],
//                    fields[1],
//                    fields[2]
//            );
//            customers.add(newCustomer);
//        }
//        for(var i : customers)
//            System.out.println(i);



//        //creating a person
//        Person alex = new Person("Alexandru Ionescu","5011228","Str. Maior Coravu 55");
//        Person ana = new Person("Ana Stan","6010502","Str. Maior Coravu 55");
//
//        //creating an account
//        Account a1 = new Account(ana,100, LocalDate.now());
//        Account a2 = new Account(alex,50, LocalDate.now());
//        Account a3 = new Account(alex,1000, LocalDate.now().minusMonths(7));
//
//        //creating a debit/savings account with an already created account
//        Debit d = new Debit(a2, "Silver");
//        Savings s = new Savings(a3, 6, 1.6);
//        Savings c = new Savings(a3, 12, 1.9);
//
//        //creating a debit/savings account with a configured person
//        Debit d2 = new Debit(alex, 1200,LocalDate.now(), "Silver");
//
//        //creating a debit/savings account with no configured entities
//        Savings s2 = new Savings("Prenume Nume","123456","Bld. Camil Ressu",15000,LocalDate.now(),12,1.5);
//
//        //creating transactions for the account
//        Transaction tr = new Transaction(d);
//
//        //creating a card for the account of the person
//        Card card = new Card(alex,d);
//
//        //instantiating the services
//        PersonService personService = PersonService.getInstance();
//        DebitService debitService = DebitService.getInstance();
//        SavingsService savingsService = SavingsService.getInstance();
//        StatementService statementService = StatementService.getInstance();
//        TransactionService transactionService = TransactionService.getInstance();
//        AccountService accountService = AccountService.getInstance();
//
//        //adding account to person
//        personService.AddAccount(alex,s);
//        personService.AddAccount(alex,d);
//        personService.AddAccount(alex,s2);
//        personService.AddAccount(alex,d2);
//
//        //adding funds
//        tr = new Transaction(d);
//        personService.AddTransaction(alex,debitService.AddFunds(d,tr,15,"ING"));
//
//        //withdrawing funds
//        tr = new Transaction(d);
//        personService.AddTransaction(alex,debitService.WithdrawFunds(d,tr,50,"BCR"));
//
//        //sending funds
//        Transaction trd = new Transaction(d2);
//        tr = new Transaction(d);
//        for(Transaction i : debitService.SendFunds(d2,d,tr,trd,5))
//            personService.AddTransaction(alex,i);
//
//
//
//        //adding funds
//        tr = new Transaction(s);
//        personService.AddTransaction(alex,savingsService.AddFunds(s,tr,15));
//
//        //withdrawing funds
//        tr = new Transaction(s);
//        personService.AddTransaction(alex,savingsService.WithdrawFunds(s,tr,50));
//
//        //sending funds
//        trd = new Transaction(s2);
//        tr = new Transaction(s);
//        for(Transaction i : savingsService.SendFunds(s,s2,tr,trd,5))
//            personService.AddTransaction(alex,i);
//
//
//        //getting account statement
//        personService.AddStatement(alex,savingsService.doStatement(s));
//        personService.AddStatement(alex,debitService.doStatement(d));
//
//        //System.out.println(alex);
//
//        Person p = new Person("","","");
//        //example of one of the services(CRUD), all services have these functions
//        personService.addPerson(alex);
//        personService.addPerson(ana);
//        personService.addPerson(p);
//        System.out.println(personService.getPersonById(1));
//        personService.updatePerson(3,ana);
//        System.out.println(personService.getPeople());
//        personService.deletePersonById(2);
//        System.out.println(personService.getPeople());

    }
}