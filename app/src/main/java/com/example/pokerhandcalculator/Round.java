package com.example.pokerhandcalculator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {

    private static Round singleton;

    public static Round getInstance() {
        if (singleton == null) {
            singleton = new Round();
        }
        return singleton;
    }

    private Round() {}

    private Map<Integer, Player> players = new HashMap<Integer, Player>();

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public void removePlayer(int id) {
        players.remove(id);
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }


}
