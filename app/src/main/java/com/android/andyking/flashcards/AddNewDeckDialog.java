package com.android.andyking.flashcards;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class AddNewDeckDialog extends DialogFragment {
    public interface OnInputListener {
        void sendInput(String input);
    }

    public OnInputListener onInputListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        View inflated = inflater.inflate(R.layout.dialog_add_deck, null);
        final EditText input = (EditText) inflated.findViewById(R.id.add_dialog_input);
        builder.setView(inflated);
        builder.setMessage(R.string.dialog_add_deck)
                .setPositiveButton(R.string.create_deck, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onInputListener.sendInput(input.getText().toString());
                        getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getDialog().dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onInputListener = (OnInputListener) getActivity();
        } catch (ClassCastException e) {
            Log.e("Error", "onAttach: " + e.getMessage());
        }
    }
}
