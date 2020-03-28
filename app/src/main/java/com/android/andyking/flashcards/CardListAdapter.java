package com.android.andyking.flashcards;

import android.app.Activity;
import androidx.fragment.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.view.MenuItem;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class CardListAdapter extends ArrayAdapter<Card> {
    public CardListAdapter(Context context, ArrayList<Card> cards) {
        super(context, 0, cards);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Card card = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_list_item, parent, false);
        }

        Button cardButton = (Button) convertView.findViewById(R.id.cardButton);
        final ImageButton settingsButton = (ImageButton) convertView.findViewById(R.id.settings_button);
        final Context context = parent.getContext();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), settingsButton);
                popup.getMenuInflater().inflate(R.menu.menu_card_settings, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.card_edit:
                                DialogFragment newFragment = AddNewCardDialog.newInstance(card);
                                FragmentManager fm = ((DeckActivity)context).getSupportFragmentManager(); ;
                                FragmentTransaction ft = fm.beginTransaction();
                                newFragment.show(fm, "dialog");

                                /*
                                    @todo
                                    This currently works to send the data to the add card app but the title of the window needs to be changed, the button needs to say Edit Card, and it needs to submit data to the card instead of creating an new one.
                                 */
                                return true;
                            case R.id.card_delete:
                                remove(card);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popup.show();
            }
        });

        cardButton.setText(card.getQuestion());
        cardButton.setTag(position);
        cardButton.setTag(R.id.hide_show, "hide");
        cardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Context context = view.getContext();
                Card card = getItem(position);
                Button button = (Button)view;

                if(button.getTag(R.id.hide_show) == "hide") {
                    button.setText(card.getAnswer());
                    button.setTag(R.id.hide_show, "show");
                }
                else {
                    button.setText(card.getQuestion());
                    button.setTag(R.id.hide_show, "hide");
                }
            }
        });

        return convertView;
    }
}
