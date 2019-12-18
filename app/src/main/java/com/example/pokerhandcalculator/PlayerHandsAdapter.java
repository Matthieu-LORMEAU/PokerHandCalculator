package com.example.pokerhandcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;


public class PlayerHandsAdapter extends ArrayAdapter<Player> {

    private ArrayList<Player> players;

    public PlayerHandsAdapter(Context context, int textViewResourceId, ArrayList<Player> players) {
        super(context, textViewResourceId, players);
        this.players = players;
    }

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.player_hand_item, null);
        }

        final Player player = getItem(position);

        if (player != null) {

            TextView playerName = v.findViewById(R.id.playerNameTextView);
            playerName.setText(player.getName().trim());

            Button removePlayerButton = v.findViewById(R.id.removePlayerButton);
            removePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove(player);
                    notifyDataSetChanged();
                }
            });

            Button foldButton = v.findViewById(R.id.foldButton);
            ImageView iv1 = v.findViewById(R.id.card1ImageView);
            ImageView iv2 = v.findViewById(R.id.card2ImageView);
            setImageCardsListeners(iv1, iv2, v);


            foldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ConstraintLayout parent = (ConstraintLayout) view.getParent();
                    Button foldButton = parent.findViewById(R.id.foldButton);
                    ImageView iv1 = parent.findViewById(R.id.card1ImageView);
                    ImageView iv2 = parent.findViewById(R.id.card2ImageView);
                    TextView foldedTextView = parent.findViewById(R.id.foldedTextView);
                    if (foldButton.getText().equals("Fold")) {
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
                }
            });
//            TextView mt = (TextView) v.findViewById(R.id.middletext);
//            TextView mtd = (TextView) v.findViewById(R.id.middletextdata);
//            TextView bt = (TextView) v.findViewById(R.id.bottomtext);
//            TextView btd = (TextView) v.findViewById(R.id.desctext);

//             check to see if each individual textview is null.
//             if not, assign some text!
//            if (iv1 != null){
//                tt.setText("Name: ");
//            }
//            if (ttd != null){
//                ttd.setText(i.getName());
//            }
//            if (mt != null){
//                mt.setText("Price: ");
//            }
//            if (mtd != null){
//                mtd.setText("$" + i.getPrice());
//            }
//            if (bt != null){
//                bt.setText("Details: ");
//            }
//            if (btd != null){
//                btd.setText(i.getDetails());
//            }
        }

        // the view must be returned to our activity
        return v;

    }

    public void setImageCardsListeners(ImageView iv1, ImageView iv2, View view) {
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.card_picker, null);

                final TableLayout ranksTableLayout = customView.findViewById(R.id.rankSelectTableLayout);
                enableOrDisableRanks(false,ranksTableLayout);

                LinearLayout suitLinearLayout = customView.findViewById(R.id.suitSelectLinearLayout);


                for (int j = 0; j < suitLinearLayout.getChildCount(); j++) {
                    suitLinearLayout.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LinearLayout parent = (LinearLayout) v.getParent();
                            for (int j = 0; j < parent.getChildCount(); j++) {
                                if (parent.getChildAt(j) instanceof ImageView) {
                                    ImageView current = (ImageView) parent.getChildAt(j);
                                    if (!current.equals(v)) {
                                        current.setAlpha(0.33f);
                                    } else {
                                        current.setAlpha(1f);
                                    }
                                }
                            }
                            enableOrDisableRanks(true,ranksTableLayout);
                        }
                    });
                }

                builder.setView(customView);
                builder.create();
                builder.show();
            }

            public void enableOrDisableRanks(boolean bool,TableLayout ranksTableLayout) {
                for (int i = 0; i < ranksTableLayout.getChildCount(); i++) {
                    TableRow row = (TableRow) ranksTableLayout.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        Button currentButton = (Button) row.getChildAt(j);
                        currentButton.setEnabled(bool);
                        if (bool){
                            currentButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
                    }
                }
            }
        });
    }


}
