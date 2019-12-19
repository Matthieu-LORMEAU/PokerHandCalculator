package com.example.pokerhandcalculator;

import android.inputmethodservice.KeyboardView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Round {

    private static Round singleton;

    public static Round getInstance() {
        if (singleton == null) {
            singleton = new Round();
            singleton.setSampleRoundForDebug();
        }
        return singleton;
    }

    private boolean[][] usedCards = new boolean[4][13];

    private Round() {
    }

    private void setSampleRoundForDebug() {
        Player player1 = new Player(0, "PLayer 1");
        player1.setFirstCard(new Card(Card.Face.Ace, Card.Suit.Clubs));
        player1.setSecondCard(new Card(Card.Face.Eight, Card.Suit.Hearts));
        Player player2 = new Player(1, "PLayer 2");
        player2.setFirstCard(new Card(Card.Face.King, Card.Suit.Clubs));
        player2.setSecondCard(new Card(Card.Face.Jack, Card.Suit.Diamonds));
        this.getPlayers().add(player1);
        this.getPlayers().add(player2);
        this.setCommunityCard(0, new Card(Card.Face.Ace, Card.Suit.Diamonds));
        this.setCommunityCard(1, new Card(Card.Face.Nine, Card.Suit.Hearts));
        this.setCommunityCard(2, new Card(Card.Face.Seven, Card.Suit.Clubs));
        this.setCommunityCard(3, new Card(Card.Face.Ten, Card.Suit.Clubs));
        this.setCommunityCard(4, new Card(Card.Face.Six, Card.Suit.Hearts));
    }

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

    public JSONObject toJSON() throws JSONException, NullCardException {
        JSONObject body = new JSONObject();
        body.put("community_cards", communityCardsToString());

        JSONArray players = new JSONArray();
        for (Player player : getPlayers()) {
            if (player.isFolded())
                continue;
            JSONObject playerJson = new JSONObject();
            playerJson.put("name", player.getName());
            playerJson.put("cards", player.getCardsAsString());
            players.put(playerJson);
        }

        body.put("players", players);

        return body;
    }

    private String communityCardsToString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : communityCards) {
            if (c != null)
                sb.append(c.toString() + " ");
        }
        return sb.toString().trim();
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
}
