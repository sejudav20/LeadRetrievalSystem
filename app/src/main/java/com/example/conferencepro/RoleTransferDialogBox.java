package com.example.conferencepro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class RoleTransferDialogBox extends DialogFragment {
    public interface RoleTransferCallback{
         void whenPositiveButtonClicked();
        void whenNegativeButtonClicked();


    }
     RoleTransferCallback listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(RoleTransferCallback) context;





    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Please be close enough to the new phone. Advertising will end after a minute or after connection is successful")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            listener.whenPositiveButtonClicked();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            listener.whenNegativeButtonClicked();
                        }
                    });

        return builder.create();
    }
}
