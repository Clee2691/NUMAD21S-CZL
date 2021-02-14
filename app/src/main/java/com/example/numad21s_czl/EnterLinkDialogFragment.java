package com.example.numad21s_czl;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.DialogFragment;

public class EnterLinkDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater lInflater = requireActivity().getLayoutInflater();
        builder.setTitle("Add New Link")
                .setView(lInflater.inflate(R.layout.dialog_enter_link, null))
        .setPositiveButton(R.string.add_link_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EnterLinkDialogFragment.this.getDialog().cancel();
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EnterLinkDialogFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
