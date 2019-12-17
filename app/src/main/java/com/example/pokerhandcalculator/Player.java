package com.example.pokerhandcalculator;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class Player {
    private int id;
    private String name;
    Card[] cards = new Card[2];

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
}
