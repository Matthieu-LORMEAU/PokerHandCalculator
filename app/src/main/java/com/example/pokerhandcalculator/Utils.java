package com.example.pokerhandcalculator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AlertDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static void setImageCardsListeners(HashMap<ImageView, Card> ivToCard) {
        for (Map.Entry item : ivToCard.entrySet()) {
            final ImageView iv = (ImageView) item.getKey();
            final Card card = (Card) item.getValue();
            final Card.Suit[] suit = new Card.Suit[1];
            final Card.Face[] face = new Card.Face[1];
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View customView = layoutInflater.inflate(R.layout.card_picker, null);
                    final TableLayout ranksTableLayout = customView.findViewById(R.id.rankSelectTableLayout);
                    enableOrDisableRanks(false, ranksTableLayout);
                    LinearLayout suitLinearLayout = customView.findViewById(R.id.suitSelectLinearLayout);

                    for (int j = 0; j < suitLinearLayout.getChildCount(); j++) {
                        final int finalJ = j;
                        suitLinearLayout.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LinearLayout parent = (LinearLayout) v.getParent();
                                suit[0] = Card.Suit.values()[(finalJ - 1) / 2];
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
                                enableOrDisableRanks(true, ranksTableLayout);
                            }
                        });
                    }
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            card.setFace(face[0]);
                            card.setSuit(suit[0]);
                            if (card.getFace() != null && card.getSuit() != null) {
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
//                    customView.findViewById(R.id.validateCardPickButton).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            card.setFace(face[0]);
//                            card.setSuit(suit[0]);
//                            String resName = card.getResourceName();
//                            int id = v.getResources().getIdentifier(resName, "drawable", v.getContext().getPackageName());
//                            iv.setImageResource(id);
//
//                        }
//                    });

                    builder.setView(customView);
                    builder.create();
                    builder.show();
                }

                public void enableOrDisableRanks(final boolean bool, final TableLayout ranksTableLayout) {
                    for (int i = 0; i < ranksTableLayout.getChildCount(); i++) {
                        TableRow row = (TableRow) ranksTableLayout.getChildAt(i);
                        for (int j = 0; j < row.getChildCount(); j++) {
                            final Button currentButton = (Button) row.getChildAt(j);
                            currentButton.setEnabled(bool);
                            if (bool) {
                                final int finalI = i;
                                final int finalJ = j;
                                currentButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        face[0] = Card.Face.values()[finalI * 5 + finalJ];
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
                            }
                        }
                    }
                }
            });
        }
    }

}
