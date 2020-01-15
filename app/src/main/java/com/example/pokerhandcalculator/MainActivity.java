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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<Player> players = Round.getInstance().getPlayers();
    PlayerHandsAdapter adapter;
    int countPlayers = 0;
    ImageView[] comCardsImageViews;
    Button displayRankingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 5; i++) {
            Round.getInstance().setCommunityCard(i, new Card());
        }
        System.out.println(Arrays.deepToString(Round.getInstance().getCommunityCards()));
        comCardsImageViews = new ImageView[]{(ImageView) findViewById(R.id.communityImage1),
                (ImageView) findViewById(R.id.communityImage2),
                (ImageView) findViewById(R.id.communityImage3),
                (ImageView) findViewById(R.id.communityImage4),
                (ImageView) findViewById(R.id.communityImage5)};

        setHandsAdapter();
        setCommunityCardsListeners();
        setClearAllCardsListener();
        this.displayRankingButton = findViewById(R.id.findWinnerButton);
        final Intent intent = new Intent(this, RankingActivity.class);
        displayRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Player p : Round.getInstance().getPlayers()) {
                    Card[] cards = p.getCards();
                    if ((cards[0].getFace() == null || cards[1].getFace() == null) && !p.isFolded()){
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setTitle("Incomplete hands !");
                        alert.setMessage("Some players are missing cards or are not folded");
                        alert.setPositiveButton("OK",null);
                        alert.show();
                        return;
                    }
                }
                startActivity(intent);
            }
        });

    }

    protected void setHandsAdapter() {
        final GridView phgv = findViewById(R.id.playerHandsGridView);
        ViewCompat.setNestedScrollingEnabled(phgv, true);
        adapter = new PlayerHandsAdapter(this, R.layout.player_hand_item, players);
        phgv.setAdapter(adapter);
        Round.getInstance().setAdapter(adapter);
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
        Utils.setImageCardsListeners(Round.getInstance().getCommunityCards(),
                comCardsImageViews);
    }

    protected void setClearAllCardsListener() {
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card[] comCards = Round.getInstance().getCommunityCards();
                for (int i = 0; i < 5; i++) {
                    comCardsImageViews[i].setImageResource(R.drawable.card_back);
                    comCards[i].setSuit(null);
                    comCards[i].setFace(null);
                }
                for (Player p : Round.getInstance().getPlayers()) {
                    for (Card c : p.getCards()) {
                        c.setSuit(null);
                        c.setFace(null);
                    }
                }
                Round.getInstance().setUsedCards(new boolean[4][13]);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
