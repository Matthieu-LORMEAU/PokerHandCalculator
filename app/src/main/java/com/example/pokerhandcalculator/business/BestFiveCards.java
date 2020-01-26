package com.example.pokerhandcalculator.business;

import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;

public class BestFiveCards extends ArrayList<Pair<BestFiveCards.CombinationLabel, Card[]>> {

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


    public BestFiveCards() {
        super();
    }

    public void addCombination(Pair<CombinationLabel, Card[]> combination) {
        this.add(combination);
    }

}
