package com.example.pokerhandcalculator;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerHandsAdapter extends ArrayAdapter<PlayerHand> {
    // declaring our ArrayList of items
    private ArrayList<PlayerHand> playerHands;

    /* here we must override the constructor for ArrayAdapter
     * the only variable we care about now is ArrayList<Item> objects,
     * because it is the list of objects we want to display.
     */
    public PlayerHandsAdapter(Context context, int textViewResourceId, ArrayList<PlayerHand> playerHands) {
        super(context, textViewResourceId, playerHands);
        this.playerHands = playerHands;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(final int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.player_hand_item, null);
        }

        /*
         * Recall that the variable position is sent in as an argument to this method.
         * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
         * iterates through the list we sent it)
         *
         * Therefore, i refers to the current Item object.
         */
        final PlayerHand playerHand = getItem(position);

        if (playerHand != null) {

            // This is how you obtain a reference to the TextViews.
            // These TextViews are created in the XML files we defined.
            TextView playerName = v.findViewById(R.id.playerNameTextView);
            playerName.setText(Integer.toString(playerHand.getId()));
            ImageView iv1 = (ImageView) v.findViewById(R.id.card1ImageView);
            ImageView iv2 = (ImageView) v.findViewById(R.id.card2ImageView);
            final LinearLayout playerHandLinearLayout = v.findViewById(R.id.playerHandLinearLayout);
            Button removePlayerButton = (Button) v.findViewById(R.id.removePlayerButton);
            removePlayerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    remove(playerHand);
                    notifyDataSetChanged();
                }
            });
            final Button foldButton = (Button) v.findViewById(R.id.foldButton);
            foldButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playerHandLinearLayout.removeAllViews();
                    System.out.println(foldButton.getText());
                    if(foldButton.getText().equals("Fold")){
                        TextView foldedTextView = new TextView(view.getContext());
                        playerHandLinearLayout.addView(foldedTextView);
                        foldedTextView.requestLayout();
                        foldedTextView.setText("FOLDED");
                        foldButton.setText("UNFOLD");
                    }else{
                        ImageView card1IV = new ImageView(view.getContext());
                        card1IV.setImageResource(R.drawable.card_back);
                        ImageView card2IV = new ImageView(view.getContext());
                        card2IV.setImageResource(R.drawable.card_back);
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                1.0f
                        );
                        card1IV.setLayoutParams(param);
                        card2IV.setLayoutParams(param);
                        playerHandLinearLayout.addView(card1IV);
                        playerHandLinearLayout.addView(card2IV);
                        foldButton.setText("Fold");
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
}
