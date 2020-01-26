package com.example.pokerhandcalculator.business;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Round {

    private static Round singleton;

    public static Round getInstance() {
        if (singleton == null) {
            singleton = new Round();
        }
        return singleton;
    }

    private Round() {
        for (int i = 0; i < 5; i++) {
            this.setCommunityCard(i, new Card());
        }
    }

    private ArrayList<Player> players = new ArrayList<Player>();

    private Card[] communityCards = new Card[5];

    public void setCommunityCard(int index, Card card) {
        if (index < 0 || index > 4)
            throw new IllegalArgumentException("Index must be from 0 to 4");
        communityCards[index] = card;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Card[] getCommunityCards() {
        return communityCards;
    }

    public boolean[][] getUsedCards() {

        boolean[][] used = new boolean[4][13];

        for (int i = 0; i < 4; i++)
            Arrays.fill(used[i], false);

        for (Card c : communityCards) {
            if (c.getSuit() != null && c.getFace() != null)
                used[c.getSuit().ordinal()][c.getFace().ordinal()] = true;
        }

        for (Player p : getPlayers()) {
            if (p.isFolded())
                continue;
            for (Card c : p.getCards()) {
                if (c.getSuit() != null && c.getFace() != null)
                    used[c.getSuit().ordinal()][c.getFace().ordinal()] = true;
            }
        }

        return used;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortPlayersByRanking() {
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player playerA, Player playerB) {
                if (playerA.isFolded() && playerB.isFolded())
                    return 0;
                else if (playerA.isFolded())
                    return 1;
                else if (playerB.isFolded())
                    return -1;

                return playerA.getRank() - playerB.getRank();
            }
        });
    }

    public Player findPlayerByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public boolean hasPlayers () {
        return players.size() != 0;
    }

    public boolean allNonFoldedPlayersHaveTwoCards() {
        for (Player p : players) {
            Card[] cards = p.getCards();
            if ((cards[0].getFace() == null || cards[1].getFace() == null) && !p.isFolded())
                return false;
        }
        return true;
    }

    public boolean nameIsAlreadyTaken(String name) {
        for (Player p : players) {
            if (p.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
