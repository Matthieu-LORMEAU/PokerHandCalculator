package com.example.pokerhandcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.webkit.HttpAuthHandler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<Player> players = Round.getInstance().getPlayers();
    PlayerHandsAdapter adapter;
    int countPlayers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHandsAdapter();
        setCommunityCardsListeners();
        Button rankingButton = findViewById(R.id.findWinnerButton);
        final Intent intent = new Intent(this, RankingActivity.class);
        rankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


    }

    protected void setHandsAdapter() {

        final GridView phgv = findViewById(R.id.playerHandsGridView);
        ViewCompat.setNestedScrollingEnabled(phgv, true);
        adapter = new PlayerHandsAdapter(this, R.layout.player_hand_item, players);
        phgv.setAdapter(adapter);

        Button addPlayer = findViewById(R.id.addPlayerButton);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlayerPopup(v);
            }
        });
    }

    protected void createPlayerPopup(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertToShow = builder.create();
        alertToShow.setTitle("Player name");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        alertToShow.setView(input);
        input.requestFocus();

        // Set up the buttons
        alertToShow.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                players.add(new Player(countPlayers++, input.getText().toString()));
                adapter.notifyDataSetChanged();
                final GridView phgv = findViewById(R.id.playerHandsGridView);
                phgv.smoothScrollToPosition(players.size() - 1);
            }
        });
        alertToShow.setButton(Dialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertToShow.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        alertToShow.show();
    }

    protected void setCommunityCardsListeners() {
        Card[] communityCards = Round.getInstance().getCommunityCards();
        HashMap<ImageView, Card> ivToCard = new HashMap<>();
        ivToCard.put((ImageView) findViewById(R.id.communityImage1), communityCards[0]);
        ivToCard.put((ImageView) findViewById(R.id.communityImage2), communityCards[1]);
        ivToCard.put((ImageView) findViewById(R.id.communityImage3), communityCards[2]);
        ivToCard.put((ImageView) findViewById(R.id.communityImage4), communityCards[3]);
        ivToCard.put((ImageView) findViewById(R.id.communityImage5), communityCards[4]);
        Utils.setImageCardsListeners(ivToCard);
    }

}
