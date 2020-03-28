package com.android.andyking.flashcards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.preference.Preference;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddNewDeckDialog.OnInputListener {

    public static ArrayList<Deck> decks;
    DeckAdapter decksAdapter;
    public static int deckID = 0;

    //@todo need to save new deck data to DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addNewDeck();
            }
        });

        // Construct the data source
        loadDecksFromDB();
        decksAdapter = new DeckAdapter(this, decks);
        GridView decksView = findViewById((R.id.deckGrid));
        decksView.setAdapter(decksAdapter);

        registerForContextMenu(decksView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadDecksFromDB();
        decksAdapter = new DeckAdapter(this, decks);
        GridView decksView = findViewById((R.id.deckGrid));
        decksView.setAdapter(decksAdapter);
    }

    public void loadDecksFromDB() {
        DB db = new DB(getApplicationContext());
        decks = db.getAllDecks();
    }

    public void addNewDeck() {
        AddNewDeckDialog dialog = new AddNewDeckDialog();
        dialog.show(getSupportFragmentManager(), "add_new_deck_dialog");
    }

    @Override
    public void sendInput(String input) { //receives input from AddNewDeckDialog
        Deck newDeck = new Deck(input);
        decks.add(newDeck);
        DB db = new DB(getApplicationContext());
        db.insertDeck(newDeck);
        decksAdapter.notifyDataSetChanged();
        //@todo it would be nice if this opened the DeckActivity you just created to save a click
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,
                    SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

