package com.android.andyking.flashcards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class AddNewCardDialog extends DialogFragment {
    public interface OnInputListener {
        void sendInput(String front, String back, Card card);
    }

    public OnInputListener onInputListener;

    static AddNewCardDialog newInstance(Card card) {
        AddNewCardDialog f = new AddNewCardDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("q", card.getQuestion());
        args.putString("a", card.getAnswer());
        args.putParcelable("card", (Parcelable)card);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = requireActivity().getLayoutInflater();
        View inflated = inflater.inflate(R.layout.dialog_add_card, null);

        boolean edit = false;
        if(getArguments() != null)
            edit = true;

        final EditText front_input = (EditText) inflated.findViewById(R.id.add_card_front_input);
        final EditText back_input = (EditText) inflated.findViewById(R.id.add_card_back_input);

        if(edit) {
            front_input.setText(getArguments().getString("q"));
            back_input.setText(getArguments().getString("a"));
        }

        String messageText = "Add Card to Deck";
        String buttonText = "Create Card";
        Card card = null;
        if(edit) {
            messageText = "Edit Card";
            buttonText = "Save Changes";
            card = getArguments().getParcelable("card");
        }

        final Card finalCard = card;
        builder.setView(inflated);
        builder.setMessage(messageText)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onInputListener.sendInput(front_input.getText().toString(), back_input.getText().toString(), finalCard);
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
