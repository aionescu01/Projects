package com.company.entities;

import java.util.Comparator;

public class Sortbydate implements Comparator<Transaction> {
    public int compare(Transaction o1, Transaction o2) {
        return -o1.getDate().compareTo(o2.getDate());
    }
}
