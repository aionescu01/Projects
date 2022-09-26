package com.company.entities;

import com.company.services.AccountService;

import java.time.LocalDate;
import java.time.LocalTime;

public class Statement {
    private Account account;
    private Debit debit;
    private Savings savings;
    private String statement = "";
    private int id;
    private static int number_of_statements=1;
    private int account_id;

    public Statement(){}

    public Statement(Account account) {
        this.account = account;
        this.statement = this.statement + "-----------------STATEMENT-------------------\n" + account.getBank() + " BANK\n" + "Debit account with id "+ account.getId() +" of " +
                account.getName() + "\nStatement made on the date: " + LocalDate.now() + " " + LocalTime.now() + "\nCurrent balance = " + account.getBalance()
                + "\nPast transactions below:\n";
        for (Transaction i : account.getTransaction_history()) {
            this.statement = this.statement + i.getTransaction() + "\n";
        }
        this.statement = this.statement + "--------------END OF STATEMENT---------------";
        this.id=number_of_statements;
        number_of_statements++;
        account_id=account.getId();
        account.addStatementId(id);
    }
    AccountService accountService = AccountService.getInstance();


    public Statement(Debit debit) {
        this.debit = debit;//sa pot sa dau get la contul pt care s a facut statementul
        this.statement = this.statement + "-----------------STATEMENT-------------------\n" + debit.getBank() + " BANK\n" + "Debit account with id "+ debit.getId() +" of " +
                debit.getName() + "\nStatement made on the date: " + LocalDate.now() + " " + LocalTime.now() + "\nCurrent balance = " + debit.getBalance()
                + "\nPast transactions below:\n";
        for (Transaction i : debit.getTransaction_history()) {
            this.statement = this.statement + i.getTransaction() + "\n";
        }
        this.statement = this.statement + "--------------END OF STATEMENT---------------";
        this.id=number_of_statements;
        number_of_statements++;
        debit.addStatementId(id);
        account_id=debit.getId();
        debit.personService.AddStatement(new Person(debit.getName(),debit.getUID(),debit.getAddress(),debit.getPerson_id(),debit.getAccounts(),debit.getStatements_history(),debit.getTransaction_history()),this);
    }

    public Statement(Savings savings) {
        this.savings = savings;
        this.statement = this.statement + "-----------------STATEMENT-------------------\n" + savings.getBank() + " BANK\n" + "Savings account with id "+ savings.getId() +" of " +
                savings.getName() + "\nStatement made on the date: " + LocalDate.now() + " " + LocalTime.now() + "\nCurrent balance = " + savings.getBalance()
                + "\nPast transactions below:\n";
        for (Transaction i : savings.getTransaction_history()) {
            this.statement = this.statement + i.getTransaction() + "\n";
        }
        this.statement = this.statement + "--------------END OF STATEMENT---------------";
        this.id=number_of_statements;
        account_id=savings.getId();
        savings.addStatementId(id);
        number_of_statements++;
    }

    public int getAccount_id() {
        return account_id;
    }

    @Override
    public String toString() {
        return "Statement{" +
                statement +
                '}';
    }

    public String toCSV() {

        statement = statement.replaceAll("\n", " \\n ");

        if(this.account.getId()!=0)
        return  this.account.getId() +
                ";" + statement;
        else if(this.debit.getId()!=0)
            return  this.debit.getId() +
                    ";" + statement;
        else
            return  this.savings.getId() +
                    ";" + statement;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}