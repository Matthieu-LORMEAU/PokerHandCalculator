package com.example.pokerhandcalculator;

import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

public class PlayerHand {
    private int id ;
    Object[] cards = new Object[2];

    public PlayerHand(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
