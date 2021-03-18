package com.jonburleson.utilitybelt;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

public class DialogFragmentTwoBtn extends androidx.fragment.app.DialogFragment {

    public Context c;
    private Listener resultListener;
    public final static String TITLE = "title";
    public final static String MESSAGE = "message";

    public DialogFragmentTwoBtn(Context c) {
        this.c = c.getApplicationContext();
    }

    interface Listener{
        void askMethod(String resultCode);
    }

    public void setListener(Listener listener) {
        try {
            resultListener = listener;
        } catch (Throwable t) {
            Toast.makeText(c, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_dialog,null);

        builder.setView(view);

        Bundle args = getArguments();
        String title = null;
        String msg = null;
        if (args != null) {
            title = args.getString(TITLE, "default title");
            msg = args.getString(MESSAGE, "default message");
        }

        TextView alertTitle = view.findViewById(R.id.alert_title);
        alertTitle.setText(title);

        TextView alertMessage = view.findViewById(R.id.alert_message);
        alertMessage.setText(msg);

        Button btnOk = view.findViewById(R.id.button_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultListener.askMethod("RESULT_OK");
                dismiss();
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