package com.company.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Card {
    private Person card_holder;
    private Account account;
    private long card_number;
    private static List<Long> used_numbers = new ArrayList<>();
    private static List<Integer> account_ids = new ArrayList<>();

    @Override
    public String toString() {
        return "Card{" +
                "card_holder=" + card_holder +
                ", account=" + account +
                ", card_number=" + card_number +
                '}';
    }

    public Card() {
    }

    public Card(Person card_holder, Account account) {
        if (Objects.equals(card_holder.getUID(), account.getUID())) {
            if(!account_ids.contains(account.getId())) {
                this.card_holder = card_holder;
                this.account = account;
                account_ids.add(account.getId());
                long number = (long) (Math.random() * 10000000000000000L);
                while (used_numbers.contains(number))
                    number = (long) (Math.random() * 10000000000000000L);
                this.card_number = number;
                used_numbers.add(number);
            }
            else {
                System.out.println("Card already exists for this account!");
                this.account=null;
                this.card_holder=null;
            }
        }
        else{
            System.out.println("Card holder and account holder are different, card making failed!");
            this.account=null;
            this.card_holder=null;
        }
    }

    public static List<Long> getUsed_numbers() {
        return used_numbers;
    }

    public static void setUsed_numbers(List<Long> used_numbers) {
        Card.used_numbers = used_numbers;
    }

    public static List<Integer> getAccount_ids() {
        return account_ids;
    }

    public static void setAccount_ids(List<Integer> account_ids) {
        Card.account_ids = account_ids;
    }

    public Person getCard_holder() {
        return card_holder;
    }

    public void setCard_holder(Person card_holder) {
        this.card_holder = card_holder;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getCard_number() {
        return card_number;
    }

    public void setCard_number(long card_number) {
        this.card_number = card_number;
    }

    public String toCSV() {
        return  card_holder.getPerson_id() +
                ";" + account.getId();
    }
}
