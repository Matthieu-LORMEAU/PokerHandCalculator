package com.example.pokerhandcalculator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pokerhandcalculator.Model.Card;
import com.example.pokerhandcalculator.Model.Player;
import com.example.pokerhandcalculator.R;
import com.example.pokerhandcalculator.Utils;

import java.util.ArrayList;

public class PlayerHandsAdapter extends ArrayAdapter<Player> {

    private ArrayList<Player> players;

    public PlayerHandsAdapter(Context context, int textViewResourceId, ArrayList<Player> players) {
        super(context, textViewResourceId, players);
        this.players = players;
    }

    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.player_hand_item, null);
            v.setClipToOutline(true);
        }

        final Player player = getItem(position);


        if (player != null) {

            TextView playerName = v.findViewById(R.id.playerNameTextView);
            playerName.setText(player.getName().trim());

            Button removePlayerButton = v.findViewById(R.id.removePlayerButton);
            removePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    player.addOrRemovePlayerCardsToUsedCards(false);
                    players.remove(player);
                    notifyDataSetChanged();
                }
            });

            final Button foldButton = v.findViewById(R.id.foldButton);
            final ImageView iv1 = v.findViewById(R.id.card1ImageView);
            final ImageView iv2 = v.findViewById(R.id.card2ImageView);
            final TextView foldedTextView = v.findViewById(R.id.foldedTextView);

            if (player.isFolded()) {
                foldButton.setText("Unfold");
                foldedTextView.setVisibility(TextView.VISIBLE);
                iv1.setVisibility(ImageView.GONE);
                iv2.setVisibility(ImageView.GONE);
            } else {
                foldButton.setText("Fold");
                foldedTextView.setVisibility(TextView.GONE);
                iv1.setVisibility(ImageView.VISIBLE);
                iv2.setVisibility(ImageView.VISIBLE);
            }

            Card[] cards = player.getCards();

            String resName1 = cards[0].getResourceName();
            if(resName1!=null){
                int id1 = v.getResources().getIdentifier(resName1, "drawable", v.getContext().getPackageName());
                iv1.setImageResource(id1);
            }
            else{
                iv1.setImageResource(R.drawable.card_back);
            }

            String resName2 = cards[1].getResourceName();
            if(resName2!=null){
                int id2 = v.getResources().getIdentifier(resName2, "drawable", v.getContext().getPackageName());
                iv2.setImageResource(id2);
            }
            else{
                iv2.setImageResource(R.drawable.card_back);
            }

            Utils.setImageCardsListeners(cards,new ImageView[]{iv1,iv2});

            foldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!player.isFolded()) {
                        foldButton.setText("Unfold");
                        foldedTextView.setVisibility(TextView.VISIBLE);
                        iv1.setVisibility(ImageView.GONE);
                        iv2.setVisibility(ImageView.GONE);
                        player.addOrRemovePlayerCardsToUsedCards(false);
                        player.fold();
                    } else {
                        foldButton.setText("Fold");
                        foldedTextView.setVisibility(TextView.GONE);
                        iv1.setVisibility(ImageView.VISIBLE);
                        iv2.setVisibility(ImageView.VISIBLE);
                        player.addOrRemovePlayerCardsToUsedCards(true);
                        player.unfold();
                    }
                }
            });
        }
        return v;
    }
}
