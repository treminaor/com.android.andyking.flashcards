package com.android.andyking.flashcards;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DeckActivity extends AppCompatActivity implements AddNewCardDialog.OnInputListener {
    Deck deck;
    CardListAdapter cardsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        deck = intent.getParcelableExtra("Deck");
        setTitle(deck.getName());

        cardsListAdapter = new CardListAdapter(this, (ArrayList<Card>) deck.getChildren());

        GridView cardsView = findViewById((R.id.cardsGrid));
        cardsView.setAdapter(cardsListAdapter);
        cardsListAdapter.notifyDataSetChanged();

        cardsListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                DB db = new DB(getApplicationContext());
                db.updateDeck(deck);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewCardDialog dialog = new AddNewCardDialog();
                dialog.show(getSupportFragmentManager(), "add_new_card_dialog");
            }
        });
    }

    @Override
    public void sendInput(String front, String back, Card card) { //receives input from AddNewDeckDialog
        if(card != null) {
            card.setQuestion(front);
            card.setAnswer(back);
        }
        else {
            Card newCard = new Card(front, back);
            deck.addChild(newCard);
        }

        cardsListAdapter.notifyDataSetChanged();
    }
}
