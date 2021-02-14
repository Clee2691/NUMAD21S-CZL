package com.example.numad21s_czl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class EnterLinkDialogFragment extends AppCompatDialogFragment {
    private EditText eTLinkName;
    private EditText eTLinkTarget;
    private addLinkListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater lInflater = requireActivity().getLayoutInflater();
        View dialogView = lInflater.inflate(R.layout.dialog_enter_link, null);
        builder.setTitle("Add New Link")
                .setView(dialogView)
        .setPositiveButton(R.string.add_link_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String linkName = eTLinkName.getText().toString();
                String linkTarget = eTLinkTarget.getText().toString();
                listener.addCard(linkName, linkTarget);
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EnterLinkDialogFragment.this.getDialog().cancel();
            }
        });
        eTLinkName = dialogView.findViewById(R.id.editTextName);
        eTLinkTarget = dialogView.findViewById(R.id.editTextLinkTarget);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (addLinkListener) context;
    }

    /*
     * An interface needed to use the text entered from the user
     * Implemented in the ActivityLinkCollector class
     */
    public interface addLinkListener {
        void addCard(String linkName, String linkTarget);
    }
}
