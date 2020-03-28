package com.android.andyking.flashcards;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class DeckAdapter extends ArrayAdapter<Deck> {
    public DeckAdapter(Context context, ArrayList<Deck> decks) {
        super(context, 0, decks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Deck deck = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.deck_list_item, parent, false);
        }

        Button deckButton = (Button) convertView.findViewById(R.id.deckButton);
        deckButton.setText(deck.getName());
        deckButton.setTag(position);
        deckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Context context = view.getContext();
                Deck deck = getItem(position);
                Intent intent = new Intent(context, DeckActivity.class);
                intent.putExtra("Deck", (Parcelable) deck);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
