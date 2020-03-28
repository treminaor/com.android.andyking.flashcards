package com.android.andyking.flashcards;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Deck implements Parcelable {
    private String name;
    private int dbID;
    private List<Card> children;

    public Deck() {
        name = "New Deck";
        dbID = ++MainActivity.deckID;
        children = new ArrayList<Card>();
    }
    public Deck(String n) {
        name = n;
        dbID = ++MainActivity.deckID;
        children = new ArrayList<Card>();
    }

    public String getName() {
        return name;
    }
    public void setName(String n) {
        name = n;
    }

    public int getDbID() { return dbID; }
    public void setDbID(int id) { dbID = id; }

    public List<Card> getChildren() {
        return children;
    }
    public void setChildren(List<Card> c) {
        children = c;
    }
    public void addChild(Card c) {
        children.add(c);
    }
    public void removeChild(Card c) {
        //@todo
    }

    //write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(name);
        dest.writeInt(dbID);
        dest.writeTypedList(children);
    }

    //constructor used for parcel
    public Deck(Parcel parcel){
        name = parcel.readString();
        dbID = parcel.readInt();
        children = new ArrayList<Card>();
        parcel.readTypedList(children, Card.CREATOR);
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Deck> CREATOR = new Parcelable.Creator<Deck>(){

        @Override
        public Deck createFromParcel(Parcel parcel) {
            return new Deck(parcel);
        }

        @Override
        public Deck[] newArray(int size) {
            return new Deck[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}

