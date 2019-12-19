package com.example.pokerhandcalculator;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PlayerRanksAdapter extends RecyclerView.Adapter<PlayerRanksAdapter.MyViewHolder> {

    ArrayList<Player> players = Round.getInstance().getPlayers();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView playerNameTV;
        public TextView playerRankTV;
        public LinearLayout playerCombinationLL;
        public TextView combTV;
        public TextView highestCardTV;

        public MyViewHolder(View v) {
            super(v);
            playerNameTV = v.findViewById(R.id.playerNameTextView);
            playerRankTV = v.findViewById(R.id.playerRankTextView);
            playerCombinationLL = v.findViewById(R.id.playerCombinationLinearLayout);
            combTV = v.findViewById(R.id.combinationTextView);
            highestCardTV = v.findViewById(R.id.highestCardTextView);
        }
    }

    public PlayerRanksAdapter(ArrayList<Player> players) {
        this.players = players;
    }

    @Override
    public PlayerRanksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_rank_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlayerRanksAdapter.MyViewHolder holder, int position) {
        Player currentPlayer = players.get(position);
        holder.playerNameTV.setText(currentPlayer.getName());
        holder.playerRankTV.setText("Rank#" + (position + 1));
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
