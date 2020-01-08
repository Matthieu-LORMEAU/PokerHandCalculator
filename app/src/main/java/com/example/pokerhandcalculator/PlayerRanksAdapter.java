package com.example.pokerhandcalculator;


import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
                ImageView iv = null;
                switch (j) {
                    case 0:
                        iv = holder.playerCombinationLL.findViewById(R.id.card1ImageView);
                        break;
                    case 1:
                        iv = holder.playerCombinationLL.findViewById(R.id.card2ImageView);
                        break;
                    case 2:
                        iv = holder.playerCombinationLL.findViewById(R.id.card3ImageView);
                        break;
                    case 3:
                        iv = holder.playerCombinationLL.findViewById(R.id.card4ImageView);
                        break;
                    case 4:
                        iv = holder.playerCombinationLL.findViewById(R.id.card5ImageView);
                        break;
                     default: break;

                }
                String resName = c.getResourceName();
                try {
                    Field idField = R.drawable.class.getDeclaredField(resName);
                    iv.setImageResource(idField.getInt(idField));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                j++;
            }

            i++;
        }
        holder.playerNameTV.setText(currentPlayer.getName());
        holder.playerRankTV.setText("Rank#" + currentPlayer.getRank());



//        holder.combTV.setText();
//        holder.highestCardTV.setText();
//        for (int i = 0; i < holder.playerCombinationLL.getChildCount(); i++) {
//            ((ImageView) holder.playerCombinationLL.getChildAt(i)).setImageResource(R.drawable.);
//        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
