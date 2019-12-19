package com.example.pokerhandcalculator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;

public class Round {

    private static Round singleton;

    public static Round getInstance() {
        if (singleton == null) {
            singleton = new Round();
//            singleton.setSampleRoundForDebug();
        }
        return singleton;
    }

    private boolean[][] usedCards = new boolean[4][13];

    private Round() {
    }

//    private void setSampleRoundForDebug() {
//        Player player1 = new Player(0, "PLayer 1");
//        player1.setFirstCard(new Card(Card.Face.Ace, Card.Suit.Clubs));
//        player1.setSecondCard(new Card(Card.Face.Eight, Card.Suit.Hearts));
//        Player player2 = new Player(1, "PLayer 2");
//        player2.setFirstCard(new Card(Card.Face.King, Card.Suit.Clubs));
//        player2.setSecondCard(new Card(Card.Face.Jack, Card.Suit.Diamonds));
//        this.getPlayers().add(player1);
//        this.getPlayers().add(player2);
//        this.setCommunityCard(0, new Card(Card.Face.Ace, Card.Suit.Diamonds));
//        this.setCommunityCard(1, new Card(Card.Face.Nine, Card.Suit.Hearts));
//        this.setCommunityCard(2, new Card(Card.Face.Seven, Card.Suit.Clubs));
//        this.setCommunityCard(3, new Card(Card.Face.Ten, Card.Suit.Clubs));
//        this.setCommunityCard(4, new Card(Card.Face.Six, Card.Suit.Hearts));
//    }

    private ArrayList<Player> players = new ArrayList<Player>();

    private PlayerHandsAdapter adapter;

    private Card[] communityCards = new Card[5];

    public void setCommunityCard(int index, Card card) {
        if (index < 0 || index > 4)
            throw new IllegalArgumentException("Index must be from 0 to 4");
        communityCards[index] = card;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    // private boolean isLegal(String visitorMessage) {
    // if (visitorMessage == null) visitorMessage = new String();
    //
    //
    // }

    public Card[] getCommunityCards() {
        return communityCards;
    }

    public boolean[][] getUsedCards() {
        return usedCards;
    }

    public void setUsedCards(boolean[][] usedCards) {
        this.usedCards = usedCards;
    }

    public void setUsedOrUnusedCard(int suit, int face, boolean used) {
        this.usedCards[suit][face] = used;
    }

    public void setAdapter(PlayerHandsAdapter adapter) {
        this.adapter = adapter;
    }

    public PlayerHandsAdapter getAdapter() {
        return adapter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortPlayersByRanking() {
        players.sort(new Comparator<Player>() {
            @Override
            public int compare(Player playerA, Player playerB) {
                if (playerA.isFolded() && playerB.isFolded())
                    return 0;
                else if (playerA.isFolded())
                    return -1;
                else if (playerB.isFolded())
                    return 1;

                return playerB.getRank() - playerA.getRank();
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
}
