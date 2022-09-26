package com.company;

import com.company.entities.*;
import com.company.entities.Statement;
import com.company.repos.AccountRepository;
import com.company.repos.PersonRepository;
import com.company.repos.StatementRepository;
import com.company.repos.TransactionRepository;
import com.company.services.*;

import java.lang.reflect.Array;
import java.sql.*;

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

    public static void main(String[] args) throws SQLException {

        PersonService personService = PersonService.getInstance();
        AccountService accountService = AccountService.getInstance();
        TransactionService transactionService = TransactionService.getInstance();
        StatementService statementService = StatementService.getInstance();
        DebitService debitService = DebitService.getInstance();
        SavingsService savingsService = SavingsService.getInstance();


        PersonRepository personRepo = new PersonRepository();
        AccountRepository accountRepo = new AccountRepository();
        TransactionRepository transactionRepo = new TransactionRepository();
        StatementRepository statementRepo = new StatementRepository();




//        Creating tables
//        personRepo.CreateTable();
//        accountRepo.CreateTable();
//        transactionRepo.CreateTable();
//        statementRepo.CreateTable();

        //Adding to tables
//        Person alex = new Person("Alex","5011228","Strada Florilor 55");
//        Person alex2 = new Person("Aaa","1234","Aleea Eprubetei 14");
//        Account a1 = new Account(alex,50, LocalDate.now());
//        Debit a2 = new Debit(alex2,50, LocalDate.now(),"gold");
//        Debit a3 = new Debit(alex,50, LocalDate.now(),"gold");
//        Savings a4 = new Savings(alex,50,LocalDate.now(),6,1.5);
//        Transaction tranzactie = new Transaction(a3);
//        Transaction tranzactie2 = new Transaction(a2);
//        transactionService.addTransaction(tranzactie);
//        transactionService.addTransaction(tranzactie2);
//        accountService.AddFunds(a3,tranzactie,10,"ING");
//        accountService.AddFunds(a2,tranzactie2,125,"ING");
//        com.company.entities.Statement st = new com.company.entities.Statement(a3);
//        com.company.entities.Statement st2 = new com.company.entities.Statement(a2);
//        personService.AddAccount(alex,a1);
//        personService.AddAccount(alex,a3);
//        personService.AddAccount(alex,a4);
//        personService.AddAccount(alex2,a2);
//        personService.AddTransaction(alex,tranzactie);
//        personService.AddTransaction(alex2,tranzactie2);
//        personService.AddStatement(alex,st);
//        personService.AddStatement(alex2,st2);
//        personRepo.addPerson(alex2);
//        personRepo.addPerson(alex);
//        accountRepo.addAccount(a1);
//        accountRepo.addDebit(a3);
//        accountRepo.addSavings(a4);
//        accountRepo.addDebit(a2);
//        transactionRepo.addTransaction(tranzactie);
//        statementRepo.addStatement(st);
//        transactionRepo.addTransaction(tranzactie2);
//        statementRepo.addStatement(st2);



        //Reading from tables and adding into services
//        personRepo.readPeople();
//        accountRepo.readAccounts();
//        transactionRepo.readTransactions();
//        statementRepo.readStatements();
//        System.out.println(personService.getPeople());
//        System.out.println(accountService.getAccounts());
//        System.out.println(transactionService.getTransactions());
//        System.out.println(statementService.getstatements());


        //Updating a statement
//        Statement s = statementService.getStatementById(1);
//        System.out.println(s);
//        s.setStatement("-----------------STATEMENTT-------------------\n" +
//                "ING BANK\n" +
//                "Debit account with id 1001 of Aaa\n" +
//                "Statement made on the date: 2022-05-23 14:44:21.043563900\n" +
//                "Current balance = 175.0\n" +
//                "Past transactions below:\n" +
//                "Added the sum of 125.0 to account on the date 2022-05-23 14:44:21.004061700 from bank ING account balance = 175.0\n" +
//                "--------------END OF STATEMENT---------------");
//        statementRepo.updateStatement(1,s);


        //Update transaction
//        Transaction t = transactionService.getTransactionById(1);
//        t.setSum(120);
//        transactionRepo.updateTransaction(1,t);



        //        Deleting entries from tables
//        accountRepo.deleteAllAccounts();
//        personRepo.deleteAllPeople();
//        transactionRepo.deleteAllTransactions();
//        statementRepo.deleteAllStatements();

//        Deleting tables
//        accountRepo.deleteAccountsTable();
//        personRepo.deletePeopleTable();
//        transactionRepo.deleteTransactionsTable();
//        statementRepo.deleteStatementsTable();


    }
}