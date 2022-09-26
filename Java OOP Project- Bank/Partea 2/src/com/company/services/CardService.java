package com.company.services;

import com.company.entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CardService {

    private List<Card> cards = new ArrayList<>();
    private static CardService instance;

    private CardService(){}

    public static CardService getInstance(){
        if(instance==null){
            instance=new CardService();
        }
        return instance;
    }
    

    public List<Card> getCards(){
        return new ArrayList<>(this.cards);
    }

    public Card getCardById(int id){
        Card Card = new Card();
        for(Card p : this.cards)
            if(p.getCard_number()==id)
                Card=p;
        return Card;
    }

    public void updateCard(int id, Card Card){
        for(Card p : this.cards)
            if(p.getCard_number()==id)
            {
                this.cards.remove(p);
                Card.setCard_number(id);
                this.cards.add(Card);
                return;
            }
    }

    public void addCard(Card Card){
        this.cards.add(Card);
    }

    public void deleteCardById(int id){
        for(Card p : this.cards)
            if(p.getCard_number()==id)
            {
                this.cards.remove(p);
                return;
            }
    }

    public void deleteCards(){
        this.cards.clear();
    }

    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))) {
            var headerLine = in.readLine();
            String line;

            while((line = in.readLine()) != null ) {
                //String[] fields = line.replaceAll(" ", "").split(",");
                String[] fields = line.replaceAll("\"", "").split(";");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("No saved cards!");
        }

        return columns;
    }

    public void loadFromCSV() {
        PersonService personService = PersonService.getInstance();
        AccountService accountService = AccountService.getInstance();
            var columns = CardService.getCSVColumns("src/com/company/data/cards.csv");
            for(var fields : columns){

                Person p = personService.getPersonById(Integer.parseInt(fields[0]));
                Account a = accountService.getAccountById(Integer.parseInt(fields[1]));
                var newCard = new Card(
                        p,
                        a
                );
                cards.add(newCard);

            }

    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("src/com/company/data/cards.csv");
            for(var customer : this.cards){
                writer.write(customer.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
