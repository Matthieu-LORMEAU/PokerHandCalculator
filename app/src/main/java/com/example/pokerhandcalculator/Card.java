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

    @Override
    public String toString() {
        String output = "";

        switch (this.face) {
            case Ace:   output += 'A'; break;
            case One:   output += '1'; break;
            case Two:   output += '2'; break;
            case Three: output += '3'; break;
            case Four:  output += '4'; break;
            case Five:  output += '5'; break;
            case Six:   output += '6'; break;
            case Seven: output += '7'; break;
            case Eight: output += '8'; break;
            case Nine:  output += '9'; break;
            case Ten:   output += 'T'; break;
            case Jack:  output += 'J'; break;
            case Queen: output += 'Q'; break;
            case King:  output += 'K'; break;
            default: break;
        }

        switch (this.suit) {
            case Hearts:    output += 'H'; break;
            case Spades:    output += 'S'; break;
            case Clubs:     output += 'C'; break;
            case Diamonds:  output += 'D'; break;
            default: break;
        }

        return output;
    }
}
