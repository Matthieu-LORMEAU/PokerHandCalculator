package com.example.pokerhandcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Player> players = new ArrayList<Player>();
    PlayerHandsAdapter adapter;
    int countPlayerHands = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHandsAdapter();
    }

    protected void setHandsAdapter(){
        final GridView phgv = findViewById(R.id.playerHandsGridView);
        ViewCompat.setNestedScrollingEnabled(phgv,true);
        adapter = new PlayerHandsAdapter(this, R.layout.player_hand_item, players);
        phgv.setAdapter(adapter);
        Button addPlayer = findViewById(R.id.addPlayerButton);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players.add(new Player(countPlayerHands++));
                adapter.notifyDataSetChanged();
                phgv.smoothScrollToPosition(players.size()-1);
            }
        });
    }
}
