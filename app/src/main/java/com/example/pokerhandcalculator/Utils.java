package com.example.pokerhandcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class Utils {

    public static void setImageCardsListeners(List<ImageView> ivs) {
        for (ImageView iv : ivs){
            iv.setOnClickListener(new View.OnClickListener() {
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

}
