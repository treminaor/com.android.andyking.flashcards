package com.android.andyking.flashcards;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Card implements Parcelable {
    private String question;
    private String answer;

    public Card(Deck d) {
        setQuestion("");
        setAnswer("");
        setParent(d);
    }

    public Card(String q, String a) {
        setQuestion(q);
        setAnswer(a);
    }
    public Card(String q, Deck d) {
        setQuestion(q);
        setAnswer("");
        setParent(d);
    }

    public Card(String q, String a, Deck d) {
        setQuestion(q);
        setAnswer(a);
        setParent(d);
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String q) {
        question = q;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String a) {
        answer = a;
    }

    public void setParent(Deck d) {
        d.addChild(this);
    }


    //write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(getQuestion());
        dest.writeString(getAnswer());
    }

    //constructor used for parcel
    public Card(Parcel parcel){
        setQuestion(parcel.readString());
        setAnswer(parcel.readString());
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>(){

        @Override
        public Card createFromParcel(Parcel parcel) {
            return new Card(parcel);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}
