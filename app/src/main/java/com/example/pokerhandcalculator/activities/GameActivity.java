package com.example.pokerhandcalculator.activities;

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

import com.example.pokerhandcalculator.R;
import com.example.pokerhandcalculator.adapters.PlayerHandsAdapter;
import com.example.pokerhandcalculator.adapters.Utils;
import com.example.pokerhandcalculator.io.ApiConsumer;
import com.example.pokerhandcalculator.business.Card;
import com.example.pokerhandcalculator.business.Player;
import com.example.pokerhandcalculator.business.Round;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    ArrayList<Player> players = Round.getInstance().getPlayers();
    PlayerHandsAdapter adapter;
    int countPlayers = 0;
    ImageView[] comCardsImageViews;
    Button displayRankingButton;

    AlertDialog.Builder errorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ApiConsumer().wakeUpServer(this);

        System.out.println(Arrays.deepToString(Round.getInstance().getCommunityCards()));
        comCardsImageViews = new ImageView[]{(ImageView) findViewById(R.id.communityImage1),
                (ImageView) findViewById(R.id.communityImage2),
                (ImageView) findViewById(R.id.communityImage3),
                (ImageView) findViewById(R.id.communityImage4),
                (ImageView) findViewById(R.id.communityImage5)};

        errorDialog = new AlertDialog.Builder(this);
        errorDialog.setPositiveButton("Ok", null);

        setHandsAdapter();
        setCommunityCardsListeners();
        setClearAllCardsListener();

        this.displayRankingButton = findViewById(R.id.findWinnerButton);

        final Intent intent = new Intent(this, RankingActivity.class);

        displayRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Player> players = Round.getInstance().getPlayers();
                if (Round.getInstance().hasPlayers() == false) {
                    showError("No players in the game", "Please add at least one player");
                    return;
                }
                if (Round.getInstance().allNonFoldedPlayersHaveTwoCards() == false) {
                    showError("Incomplete hands", "Some non folded players are missing cards");
                    return;
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
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        alertToShow.setView(input);
        input.requestFocus();
        alertToShow.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameInput = input.getText().toString();
                if (nameInput.isEmpty()) {
                    showError("Name is empty", "Please set a non empty name");
                    return;
                }
                else if (Round.getInstance().nameIsAlreadyTaken(nameInput)) {
                    showError("Another player has this name", "Please set another name");
                    return;
                }
                players.add(new Player(countPlayers++, nameInput.toString()));
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
                adapter.notifyDataSetChanged();
            }
        });
    }

    protected  void showError(String title, String message) {
        errorDialog.setTitle(title);
        errorDialog.setMessage(message);
        errorDialog.show();
    }

}
