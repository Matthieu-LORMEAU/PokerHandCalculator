package com.example.pokerhandcalculator.adapters;


import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokerhandcalculator.business.BestFiveCards;
import com.example.pokerhandcalculator.business.Card;
import com.example.pokerhandcalculator.business.Player;
import com.example.pokerhandcalculator.R;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class PlayerRanksAdapter extends RecyclerView.Adapter<PlayerRanksAdapter.MyViewHolder> {

    ArrayList<Player> players;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView playerNameTV;
        public TextView playerRankTV;
        public LinearLayout playerCombinationLL;
        public TextView comb1TV;
        public TextView comb2TV;

        public MyViewHolder(View v) {
            super(v);
            playerNameTV = v.findViewById(R.id.playerNameTextView);
            playerRankTV = v.findViewById(R.id.playerRankTextView);
            playerCombinationLL = v.findViewById(R.id.playerCombinationLinearLayout);
            comb1TV = v.findViewById(R.id.combination1TextView);
            comb2TV = v.findViewById(R.id.combination2TextView);
        }
    }

    public PlayerRanksAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public PlayerRanksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_rank_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlayerRanksAdapter.MyViewHolder holder, int position) {

        Player currentPlayer = players.get(position);

        ImageView ivCard1 = holder.playerCombinationLL.findViewById(R.id.card1ImageView);
        ImageView ivCard2 = holder.playerCombinationLL.findViewById(R.id.card2ImageView);
        ImageView ivCard3 = holder.playerCombinationLL.findViewById(R.id.card3ImageView);
        ImageView ivCard4 = holder.playerCombinationLL.findViewById(R.id.card4ImageView);
        ImageView ivCard5 = holder.playerCombinationLL.findViewById(R.id.card5ImageView);
        ImageView[] ivCards = new ImageView[]{ivCard1, ivCard2, ivCard3, ivCard4, ivCard5};

        holder.playerNameTV.setText(currentPlayer.getName());

        if (currentPlayer.isFolded()) {
            for (ImageView iv : ivCards) {
                iv.setVisibility(View.GONE);
            }
            holder.comb1TV.setVisibility(View.GONE);
            holder.comb2TV.setVisibility(View.GONE);
            holder.playerRankTV.setText("Folded");
            return;
        }

        BestFiveCards bestFive = currentPlayer.getBestFive();

        int i = 0; // iterate over combinations
        int j = 0; // counts the number of card already filled
        for (Pair<BestFiveCards.CombinationLabel, Card[]> comb : bestFive) {
            if (i == 0) {
                holder.comb1TV.setText(comb.first.name);
            }
            else if (i == 1) {
                holder.comb2TV.setText(comb.first.name);
            }

            for (Card c : comb.second) {
                String resName = c.getResourceName();
                try {
                    Field idField = R.drawable.class.getDeclaredField(resName);
                    ivCards[j].setImageResource(idField.getInt(idField));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                j++;
            }

            i++;
        }

        if (i < 2) {
            holder.comb2TV.setVisibility(View.GONE);
        }

        if (j <= 4) {
            holder.playerCombinationLL.findViewById(R.id.card5ImageView).setVisibility(ImageView.GONE);
            if (j <= 3) {
                holder.playerCombinationLL.findViewById(R.id.card4ImageView).setVisibility(ImageView.GONE);
                if (j <= 2) {
                    holder.playerCombinationLL.findViewById(R.id.card3ImageView).setVisibility(ImageView.GONE);
                }
            }
        }

        int rank = currentPlayer.getRank();
        String rankLabel = rank == 1 ? "Winner !" : "#"+rank;
        holder.playerRankTV.setText(rankLabel);


    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
