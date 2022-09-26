package com.company.entities;

import java.util.*;

public class Person {
   private String name;
   private String UID;
   private String address;
   private static int number_of_people=1;
   private int person_id;
   //private List<Account> accounts = new Vector<>();
   //private List<Statement> statements_history = new Vector<>();
   //private List<Transaction> transaction_history = new Vector<>();
   private List<Account> accounts = new Vector<>();
   private List<Statement> statements_history = new Vector<>();
   private List<Transaction> transaction_history = new Vector<>();
   private List<Integer> statement_ids = new Vector<>();
   private List<Integer> transaction_ids = new Vector<>();
   private List<Integer> account_ids = new Vector<>();

   public void addAccountId(int id)
   {
      account_ids.add(id);
   }

   public List<Integer> getAccount_ids() {
      return account_ids;
   }

   public List<Integer> getStatement_ids() {
      return statement_ids;
   }

   public void setStatement_ids(List<Integer> statement_ids) {
      this.statement_ids = statement_ids;
   }

   public List<Integer> getTransaction_ids() {
      return transaction_ids;
   }

   public void setTransaction_ids(List<Integer> transaction_ids) {
      this.transaction_ids = transaction_ids;
   }



   public void addStatementId(int id){
      statement_ids.add(id);
   }

   public void addTransactionId(int id){
      transaction_ids.add(id);
   }

   public Person() {
      number_of_people++;
   }

   public Person(Person p) {
      this.name=p.name;
      this.UID=p.UID;
      this.address=p.address;
      this.person_id=p.person_id;
      //number_of_people++;
   }

   public Person(Account a) {
      this.name=a.getName();
      this.UID=a.getUID();
      this.address=a.getAddress();
      this.person_id=number_of_people;
      number_of_people++;
   }

   public Person(String name, String UID, String address) {
      this.name = name;
      this.UID = UID;
      this.address = address;
      this.person_id=number_of_people;
      number_of_people++;
   }

   public Person(String name, String UID, String address, int person_id, List<Account> accounts, List<Statement> statements_history, List<Transaction> transaction_history) {
      this.name = name;
      this.UID = UID;
      this.address = address;
      this.person_id = person_id;
      this.accounts = accounts;
      this.statements_history = statements_history;
      this.transaction_history = transaction_history;
   }

   public static int getNumber_of_people() {
      return number_of_people-1;
   }

   public static void setNumber_of_people(int number_of_people) {
      Person.number_of_people = number_of_people;
   }

   public int getPerson_id() {
      return person_id;
   }

   public void setPerson_id(int person_id) {
      this.person_id = person_id;
   }

   public void setAccounts(List<Account> accounts) {
      this.accounts = accounts;
   }

   public void setStatements_history(List<Statement> statements_history) {
      this.statements_history = statements_history;
   }

   public void setTransaction_history(List<Transaction> transaction_history) {
      this.transaction_history = transaction_history;
   }


   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getUID() {
      return UID;
   }

   public void setUID(String UID) {
      this.UID = UID;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public List<Account> getAccounts() {
      return accounts;
   }

   public List<Statement> getStatements_history() {
      return statements_history;
   }

   public List<Transaction> getTransaction_history() {
      return transaction_history;
   }


   @Override
   public String toString() {
      return "Person{" +
              "name='" + name + '\'' +
              ", UID='" + UID + '\'' +
              ", address='" + address + '\'' +
              ", person_id=" + person_id +
              ", accounts=" + accounts +
              ", statements_history=" + statements_history +
              ", transaction_history=" + transaction_history +
              '}';
   }

   public String toString2() {
      return "Person{" +
              "name='" + name + '\'' +
              ", UID='" + UID + '\'' +
              ", address='" + address + '\'' +
              ", person_id=" + person_id +
              '}';
   }

   public String toCSV() {
      return  name +
              ";" + UID +
              ";" + address +
              ";" + person_id;
   }

}

