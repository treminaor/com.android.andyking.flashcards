package com.android.andyking.flashcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "com.android.andyking.flashcards";
    private static final String DECKS_TABLE = "decks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_GSON = "gson";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DECKS_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_GSON + " TEXT)");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DECKS_TABLE);

        // Create tables again
        onCreate(db);
    }

    public void clearDb() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DECKS_TABLE);
        MainActivity.deckID = 0;
        // Create tables again
        onCreate(db);
    }

    public void insertDeck(Deck deck){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Gson gson = new Gson();
        String toStoreObject = gson.toJson(deck, Deck.class);
        values.put(COLUMN_GSON, toStoreObject);//column name, column value

        // Inserting Row
        db.insert(DECKS_TABLE, null, values);//tableName, nullColumnHack, CotentValues
        db.close(); // Closing database connection
    }

    public void updateDeck(Deck deck) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Gson gson = new Gson();
        String toStoreObject = gson.toJson(deck, Deck.class);
        values.put(COLUMN_GSON, toStoreObject);//column name, column value

        db.update(DECKS_TABLE, values,"ID = " + deck.getDbID(), null);
        db.close(); // Closing database connection
    }

    public Deck getDeckById(int id) {
        String selectQuery = "SELECT  * FROM " + DECKS_TABLE + " WHERE ID = " + id + " LIMIT 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String gsonData = cursor.getString(1);
        Gson gson = new Gson();

        Deck deck = gson.fromJson(gsonData, Deck.class);

        cursor.close();
        db.close();

        return deck;
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public ArrayList<Deck> getAllDecks(){
        ArrayList<Deck> list = new ArrayList<Deck>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DECKS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String gsonData = cursor.getString(1);
                Gson gson = new Gson();
                list.add(gson.fromJson(gsonData, Deck.class));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();

        return list;
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
