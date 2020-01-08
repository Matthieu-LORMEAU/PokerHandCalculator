package com.example.pokerhandcalculator;

import android.app.Dialog;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class JSONRankingParser {

    public void parse(JSONObject json) {

        Iterator<String> iter = json.keys();
        while (iter.hasNext()) {
            String nom = iter.next();
            try {
                JSONObject playerJson = json.getJSONObject(nom);
                Player player = Round.getInstance().findPlayerByName(nom);
                player.setBestFive(parseHand(playerJson));
            } catch (JSONException e) {
                // Something went wrong!
            }
        }
    }

    private BestFiveCards parseHand(JSONObject playerJson) {

        try {
            BestFiveCards bestFive = new BestFiveCards();
            JSONArray combinationsJsonArray = playerJson.getJSONArray("combinations");
            for (int i = 0; i < combinationsJsonArray.length(); i++) {
                JSONArray combinationJsonArray = combinationsJsonArray.getJSONArray(i);
                bestFive.addCombination(parseCombination(combinationJsonArray));
            }
            return bestFive;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Pair<BestFiveCards.CombinationLabel, Card[]> parseCombination(JSONArray combination) {
        try {
            BestFiveCards.CombinationLabel combinationLabel =  intToCombinationLabel(combination.getInt(0));
            JSONArray cardsJsonArray =  combination.getJSONArray(1);
            Card[] cards = new Card[cardsJsonArray.length()];
            for (int i = 0; i < cardsJsonArray.length(); i++) {
                cards[i] = parseCard(cardsJsonArray.getJSONObject(i));
            }
            return new Pair<BestFiveCards.CombinationLabel, Card[]>(combinationLabel, cards);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Card parseCard(JSONObject cardJson) {
        try {
            String face = cardJson.getString("face");
            String suit = cardJson.getString("suit");
            return new Card(stringToCardFace(face), stringToCardSuit(suit));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Card.Face stringToCardFace(String face) {
        switch (face) {
            case "1": return Card.Face.Ace;
            case "2": return Card.Face.Two;
            case "3": return Card.Face.Three;
            case "4": return Card.Face.Four;
            case "5": return Card.Face.Five;
            case "6": return Card.Face.Six;
            case "7": return Card.Face.Seven;
            case "8": return Card.Face.Eight;
            case "9": return Card.Face.Nine;
            case "T": return Card.Face.Ten;
            case "J": return Card.Face.Jack;
            case "Q": return Card.Face.Queen;
            case "K": return Card.Face.King;
            default: return Card.Face.Ace;
        }

    }

    private Card.Suit stringToCardSuit(String suit) {
        switch (suit) {
            case "H": return Card.Suit.Hearts;
            case "D": return Card.Suit.Diamonds;
            case "C": return Card.Suit.Clubs;
            default: return Card.Suit.Spades;
        }
    }

    private BestFiveCards.CombinationLabel intToCombinationLabel(int value) {
        switch (value) {
            case 1 : return BestFiveCards.CombinationLabel.HighCard;
            case 2 : return BestFiveCards.CombinationLabel.Pair;
            case 3 : return BestFiveCards.CombinationLabel.TwoPairs;
            case 4 : return BestFiveCards.CombinationLabel.ThreeOfAKind;
            case 5 : return BestFiveCards.CombinationLabel.Straight;
            case 6 : return BestFiveCards.CombinationLabel.Flush;
            case 7 : return BestFiveCards.CombinationLabel.FullHouse;
            case 8 : return BestFiveCards.CombinationLabel.FourOfAkind;
            case 9 : return BestFiveCards.CombinationLabel.StraightFlush;
            default: return BestFiveCards.CombinationLabel.RoyalFlush;

        }

    }

}
