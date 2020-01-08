package com.example.pokerhandcalculator;


import android.widget.ImageView;

public class Card {

    public enum Face {Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King}

    enum Suit {Hearts, Spades, Diamonds, Clubs}

    private Face face;
    private Suit suit;

    public Card(Face face, Suit suit) {
        this.face = face;
        this.suit = suit;
    }

    public Card() {
    }

    public Face getFace() {
        return face;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        String output = "";
        if (this.suit == null || this.face == null) {
            return null;
        }

        switch (this.face) {
            case Ace:
                output += 'A';
                break;
            case Two:
                output += '2';
                break;
            case Three:
                output += '3';
                break;
            case Four:
                output += '4';
                break;
            case Five:
                output += '5';
                break;
            case Six:
                output += '6';
                break;
            case Seven:
                output += '7';
                break;
            case Eight:
                output += '8';
                break;
            case Nine:
                output += '9';
                break;
            case Ten:
                output += 'T';
                break;
            case Jack:
                output += 'J';
                break;
            case Queen:
                output += 'Q';
                break;
            case King:
                output += 'K';
                break;
            default:
                break;
        }

        switch (this.suit) {
            case Hearts:
                output += 'H';
                break;
            case Spades:
                output += 'S';
                break;
            case Clubs:
                output += 'C';
                break;
            case Diamonds:
                output += 'D';
                break;
            default:
                break;
        }

        return output;
    }

    public String getResourceName() {
        String output = "";
        if (this.suit == null || this.face == null) {
            return null;
        }
        switch (this.suit) {
            case Hearts:
                output += "hearts";
                break;
            case Spades:
                output += "spades";
                break;
            case Clubs:
                output += "club";
                break;
            case Diamonds:
                output += "diamond";
                break;
            default:
                break;
        }

        switch (this.face) {
            case Ace:
                output += '1';
                break;
            case Two:
                output += '2';
                break;
            case Three:
                output += '3';
                break;
            case Four:
                output += '4';
                break;
            case Five:
                output += '5';
                break;
            case Six:
                output += '6';
                break;
            case Seven:
                output += '7';
                break;
            case Eight:
                output += '8';
                break;
            case Nine:
                output += '9';
                break;
            case Ten:
                output += "10";
                break;
            case Jack:
                output += "11";
                break;
            case Queen:
                output += "12";
                break;
            case King:
                output += "13";
                break;
            default:
                break;
        }
        return output;
    }
}
