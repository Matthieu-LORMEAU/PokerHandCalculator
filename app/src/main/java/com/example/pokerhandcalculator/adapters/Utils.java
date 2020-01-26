package com.example.pokerhandcalculator.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AlertDialog;

import com.example.pokerhandcalculator.business.Card;
import com.example.pokerhandcalculator.business.Round;
import com.example.pokerhandcalculator.R;

public class Utils {

    public static void setImageCardsListeners(Card[] cards, ImageView[] imageViews) {
        final Round round = Round.getInstance();
        for (int i = 0; i < cards.length; i++) {
            final ImageView iv = imageViews[i];
            final Card card = cards[i];
            final Card.Suit[] suit = new Card.Suit[1];
            final Card.Face[] face = new Card.Face[1];
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    suit[0] = null;
                    face[0] = null;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View customView = layoutInflater.inflate(R.layout.card_picker, null);
                    final TableLayout ranksTableLayout = customView.findViewById(R.id.rankSelectTableLayout);
                    LinearLayout suitLinearLayout = customView.findViewById(R.id.suitSelectLinearLayout);

                    for (int j = 0; j < suitLinearLayout.getChildCount(); j++) {
                        if (j % 2 == 0)
                            continue;
                        final int suitIndex = (j - 1) / 2;
                        ImageView cardIv = (ImageView) suitLinearLayout.getChildAt(j);
                        if (containsBoolean(round.getUsedCards()[suitIndex], false)) {
                            cardIv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LinearLayout parent = (LinearLayout) v.getParent();
                                    suit[0] = Card.Suit.values()[suitIndex];
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
                                    enableRanks(ranksTableLayout, suitIndex);
                                }
                            });
                        } else {
                            cardIv.setVisibility(View.INVISIBLE);
                        }
                    }
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (face[0] != null && suit[0] != null) {

                                Card.Suit previousSuit = card.getSuit();
                                Card.Face previousFace = card.getFace();
                                card.setFace(face[0]);
                                card.setSuit(suit[0]);
                                String resName = card.getResourceName();
                                int id = customView.getResources().getIdentifier(resName, "drawable", customView.getContext().getPackageName());
                                iv.setImageResource(id);
                            }
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.setView(customView);
                    builder.create();
                    builder.show();
                }

                public void enableRanks(final TableLayout ranksTableLayout, int suitIndex) {
                    ranksTableLayout.setVisibility(View.VISIBLE);
                    for (int i = 0; i < ranksTableLayout.getChildCount(); i++) {
                        TableRow row = (TableRow) ranksTableLayout.getChildAt(i);
                        for (int j = 0; j < row.getChildCount(); j++) {
                            final Button currentButton = (Button) row.getChildAt(j);
                            final int faceIndex = i * 5 + j;
                            if (round.getUsedCards()[suitIndex][faceIndex] == false) {
                                currentButton.setVisibility(View.VISIBLE);
                                currentButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        face[0] = Card.Face.values()[faceIndex];
                                        currentButton.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                                        for (int i = 0; i < ranksTableLayout.getChildCount(); i++) {
                                            TableRow row = (TableRow) ranksTableLayout.getChildAt(i);
                                            for (int j = 0; j < row.getChildCount(); j++) {
                                                final Button currentButton = (Button) row.getChildAt(j);
                                                if (!currentButton.equals(v)) {
                                                    currentButton.getBackground().clearColorFilter();
                                                }
                                            }
                                        }
                                    }
                                });
                            } else {
                                currentButton.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
            });
        }
    }

    private static boolean containsBoolean(boolean[] array, boolean bool) {
        for (boolean b : array) {
            if (b == bool) {
                return true;
            }
        }
        return false;
    }

}
