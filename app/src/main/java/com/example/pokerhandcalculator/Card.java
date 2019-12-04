package com.example.pokerhandcalculator;


public class Card {

    public enum Face {
        One,
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        Jack,
        Queen,
        King,
        Ace
    }

    enum Suit {
        Hearts,
        Spades,
        Clubs,
        Diamonds
    }

    private Face face;
    private Suit suit;

    public Card (Face face, Suit suit) {
        this.face = face;
        this.suit = suit;
    }

    public Face getFace() {
        return face;
    }

    public Suit getSuit() {
        return suit;
    }
    
}
