package com.android.andyking.flashcards;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.xml_preferences, rootKey);
    }


    @Override
    public boolean onPreferenceTreeClick (Preference preference)
    {
        String key = preference.getKey();
        if(key.equals("cleardb")){
            DB db = new DB(getActivity().getApplicationContext());
            db.clearDb();
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                "Decks have been erased",
                Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        else if(key.equals("showdb")) {
            Intent dbmanager = new Intent(getActivity(),AndroidDatabaseManager.class);
            startActivity(dbmanager);
        }
        return false;
    }
}
