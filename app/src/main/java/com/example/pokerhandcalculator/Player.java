package com.example.pokerhandcalculator;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class Player {
    private int id;
    private String name;
    private Card[] cards = new Card[]{new Card(),new Card()};
    boolean folded = false;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFirstCard(Card card) {
        this.cards[0] = card;
    }

    public void setSecondCard(Card card) {
        this.cards[1] = card;
    }

    public Card[] getCards() {
        return cards;
    }

    public String getCardsAsString() throws NullCardException {
        if (cards[0] == null || cards[1] == null)
            throw new NullCardException();
        return cards[0].toString() + cards[1].toString();
    }

    public void fold() {
        folded = true;
    }

    public void unfold() {
        folded = false;
    }

    public boolean isFolded() {
        return folded;
    }
}
