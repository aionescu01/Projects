    package com.company.entities;

    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.Objects;

    public class Account extends Person {
    private double balance;
    private LocalDate opening_date;
    private static int number_of_accounts = 1000;
    private int id;
    private static final String bank = "ING";

    public Account() {
        super();
    }

    public Account(Account a) {
        super(a);
        this.balance = a.balance;
        this.opening_date = a.opening_date;
        this.id = number_of_accounts;
        number_of_accounts++;
    }

    public Account(Person p, double balance, LocalDate opening_date) {
        super(p);
        this.balance = balance;
        this.opening_date = opening_date;
        this.id = number_of_accounts;
        number_of_accounts++;
    }

    public Account(String name, String UID, String address, double balance, LocalDate opening_date) {
        super(name, UID, address);
        this.balance = balance;
        this.opening_date = opening_date;
        this.id = number_of_accounts;
        number_of_accounts++;
    }

    public static int getNumber_of_accounts() {
        return number_of_accounts;
    }

    public static void setNumber_of_accounts(int number_of_accounts) {
        Account.number_of_accounts = number_of_accounts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDate getOpening_date() {
        return opening_date;
    }

    public void setOpening_date(LocalDate opening_date) {
        this.opening_date = opening_date;
    }

    public String getBank() {
        return bank;
    }

    public Statement doStatement() {
        System.out.println("statement");
        return null;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", opening_date=" + opening_date +
                ", id=" + id +
                '}';
    }
}
