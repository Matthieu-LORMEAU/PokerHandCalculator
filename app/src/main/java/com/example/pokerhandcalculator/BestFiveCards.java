package com.example.pokerhandcalculator;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class BestFiveCards {

    public enum CombinationLabel {
        HighCard("Single card", 1),
        Pair("Pair", 2),
        TwoPairs("Two pairs", 3),
        ThreeOfAKind("Three of a kind", 4),
        Straight("Straight", 5),
        Flush("Flush", 6),
        FullHouse("Full house", 7),
        FourOfAkind("Four of a kind", 8),
        StraightFlush("Straight flush", 9),
        RoyalFlush("Royal flush", 10);

        public final String name;
        public final int value;

        private CombinationLabel(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    private List<Pair<CombinationLabel, Card[]>> combinations;

    public BestFiveCards() {
        combinations = new ArrayList<Pair<CombinationLabel, Card[]>>();
    }

    public void addCombination(CombinationLabel label, Card[] cards) {
        this.combinations.add(new Pair<CombinationLabel, Card[]>(label, cards));
    }

}
