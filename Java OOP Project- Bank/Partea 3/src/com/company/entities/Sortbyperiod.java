package com.company.entities;

import java.util.Comparator;

public class Sortbyperiod implements Comparator<Account> {
    public int compare(Account o1, Account o2) {
        Savings s= new Savings();
        if (o1.getClass()==s.getClass()&&o2.getClass()==s.getClass()) {
            return ((Savings) o1).getPeriod() - ((Savings) o2).getPeriod();
        }
        else return 0;
    }

}
