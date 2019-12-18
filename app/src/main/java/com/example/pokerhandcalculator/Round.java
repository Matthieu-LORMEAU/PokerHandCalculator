package com.example.pokerhandcalculator;

import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Round {

    private static Round singleton;

    public static Round getInstance() {
        if (singleton == null) {
            singleton = new Round();
        }
        return singleton;
    }

    private boolean[][] usedCards = new boolean[4][13];

    private Round() {
    }

    private ArrayList<Player> players = new ArrayList<Player>();

    private Card[] communityCards = new Card[5];


//    public void addPlayer(Player player) {
//        players.put(player.getId(), player);
//    }

    //    public void removePlayer(int id) {
//        players.remove(id);
//    }
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


//    private boolean isLegal(String visitorMessage) {
//        if (visitorMessage == null) visitorMessage = new String();
//
//
//    }

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
}
