package com.nikhil.wifip2pconnection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created by Nikhil on 13-01-2016.
 */
public class DeviceListDialog extends DialogFragment {
    ArrayAdapter<String> mAdapter;
    MainActivity activity;

    public DeviceListDialog(){

    }
    public DeviceListDialog(ArrayAdapter<String> mAdapter, MainActivity activity) {
        this.mAdapter = mAdapter;
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select a device")
                .setSingleChoiceItems(mAdapter, 0, activity)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });

        return builder.create();
    }

}
