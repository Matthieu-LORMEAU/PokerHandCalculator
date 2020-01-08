package com.example.pokerhandcalculator;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoundSerializer {

    public RoundSerializer() {
    }

    public JSONObject getJSONOf(Round round) throws JSONException, NullCardException {
        JSONObject body = new JSONObject();
        body.put("community_cards", communityCardsToString(round));

        JSONArray players = new JSONArray();
        for (Player player : round.getPlayers()) {
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

    private String communityCardsToString(Round round) {
        StringBuilder sb = new StringBuilder();
        for (Card c : round.getCommunityCards()) {
            if (c != null)
                sb.append(c.toString() + " ");
        }
        Log.d("Round", sb.toString());
        return sb.toString().trim();
    }
}
