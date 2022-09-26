package com.company.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import com.company.services.*;

public class Debit extends Account {
    private String account_type;
    //cont tip gold sau silver
    PersonService personService = PersonService.getInstance();

    public Debit() {
    }

    public Debit(Account account, String account_type) {
        super(account);
        this.account_type=account_type;
        personService.AddAccount(this,this);
    }

    public Debit(Person p, double balance, LocalDate opening_date, String account_type) {
        super(p, balance, opening_date);
        this.account_type = account_type;
        personService.AddAccount(this,this);

    }

    public Debit(String name, String UID, String address, double balance, LocalDate opening_date, String account_type) {
        super(name, UID, address, balance, opening_date);
        this.account_type = account_type;
        personService.AddAccount(this,this);
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    @Override
    public String toString() {
        return "Debit{" +
                "balance=" + getBalance() +
                ", opening_date=" + getOpening_date() +
                ", account_type='" + account_type + '\'' +
                ", id=" + getId()+
                '}';
    }

    public String toCSV() {
        return  getId() +
                ";" + account_type;
    }


}