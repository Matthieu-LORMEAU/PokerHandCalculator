package com.example.pokerhandcalculator.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pokerhandcalculator.R;
import com.example.pokerhandcalculator.adapters.PlayerRanksAdapter;
import com.example.pokerhandcalculator.io.ApiConsumer;
import com.example.pokerhandcalculator.business.Player;
import com.example.pokerhandcalculator.business.Round;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {
    ArrayList<Player> players = Round.getInstance().getPlayers();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ranking);

        ApiConsumer apiConsumer = new ApiConsumer();
        apiConsumer.callApi(this);

        Round.getInstance().sortPlayersByRanking();

        Button newRoundButton = findViewById(R.id.backButton);
        newRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RankingActivity.super.onBackPressed();
            }
        });
    }

    public void setRanksAdapter() {
        recyclerView = findViewById(R.id.playerRanksRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PlayerRanksAdapter(players);
        recyclerView.setAdapter(mAdapter);
    }


}
