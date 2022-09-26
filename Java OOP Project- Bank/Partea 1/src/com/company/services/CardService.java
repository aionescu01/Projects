package com.company.services;

import com.company.entities.*;

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
}
