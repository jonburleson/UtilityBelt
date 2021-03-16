package com.jonburleson.utilitybelt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class DialogFragmentTwoBtn extends androidx.fragment.app.DialogFragment {

    public Context c;
    public final static String TITLE = "title";
    public final static String MESSAGE = "message";

    public DialogFragmentTwoBtn(Context c) {
        this.c = c.getApplicationContext();
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog,null);

        builder.setView(view);

        Bundle args = getArguments();
        String title = args.getString(TITLE, "default title");
        String msg = args.getString(MESSAGE, "default message");

        TextView alertTitle = view.findViewById(R.id.alert_title);
        alertTitle.setText(title);

        TextView alertMessage = view.findViewById(R.id.alert_message);
        alertMessage.setText(msg);

        Button btnOk = view.findViewById(R.id.button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete note
                Intent intent = new Intent(c, NotesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                c.startActivity(intent);
            }
        });

        Button btnCancel = view.findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return builder.create();
    }
}