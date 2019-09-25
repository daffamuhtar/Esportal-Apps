package com.majinor.esportal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditTeamDialog extends AppCompatDialogFragment {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button save,cancle;
    private ExampleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_team, null);

        builder.setView(view);

        save=view.findViewById(R.id.btn_det_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if(!username.equals("majinor")){
                    editTextPassword.setError("harus majinor");
                }else{
                    listener.applyTexts(username, password);
                    dismiss();
                }
            }
        });
        cancle=view.findViewById(R.id.btn_det_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dismiss();
            }
        });
        editTextUsername = view.findViewById(R.id.tv_det_usrang1);
        editTextPassword = view.findViewById(R.id.tv_det_ignang1);

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }
}
