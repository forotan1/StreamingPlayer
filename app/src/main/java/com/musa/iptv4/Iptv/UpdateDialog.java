package com.musa.iptv4.Iptv;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.musa.iptv4.R;


public class UpdateDialog extends AppCompatDialogFragment {

    private EditText updateTitle;
    private EditText updateUrl;
    private EditText updateIcon;
    private EditText updateAbout;
    private IpTvDBHelper dbHelper;
    private long receivedPersonId;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_layout_dialog, null);


        //init
        updateTitle = view.findViewById(R.id.ip_tv_title);
        updateUrl= view.findViewById(R.id.ip_tv_url);
        updateIcon= view.findViewById(R.id.ip_tv_icon);
        updateAbout = view.findViewById(R.id.ip_tv_about);


        dbHelper = new IpTvDBHelper(getContext());
        try {
            //get intent to get person id
            receivedPersonId = getArguments().getLong("USER_ID");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Imodel queriedImodel = dbHelper.getPerson(receivedPersonId);
        //set field to this user data
        updateTitle.setText(queriedImodel.getiTitle());
        updateUrl.setText(queriedImodel.getiUrl());
        updateAbout.setText(queriedImodel.getiAbout());
        updateIcon.setText(queriedImodel.getImage());


        builder.setView(view)
                .setTitle("Update Stream")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updatePerson();


                    }
                });
        return builder.create();



    }
    private void updatePerson(){
        String iTitle = updateTitle.getText().toString().trim();
        String iAbout = updateAbout.getText().toString().trim();
        String iUrl = updateUrl.getText().toString().trim();
        String image = updateIcon.getText().toString().trim();


        if(iTitle.isEmpty()){
            //error iTitle is empty
            Toast.makeText(getContext(), "You must enter a iTitle", Toast.LENGTH_SHORT).show();
        }

        if(iUrl.isEmpty()){
            //error iTitle is empty
            Toast.makeText(getContext(), "You must enter an age", Toast.LENGTH_SHORT).show();
        }

        if(iAbout.isEmpty()){
            //error iTitle is empty
            Toast.makeText(getContext(), "You must enter an iUrl", Toast.LENGTH_SHORT).show();
        }

        if(image.isEmpty()){
            //error iTitle is empty
            Toast.makeText(getContext(), "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        //create updated person
        Imodel updatedPerson = new Imodel(iTitle, iUrl, iAbout, image);

        //call dbhelper update
        dbHelper.updatePersonRecord(receivedPersonId, getContext(), updatedPerson);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        IpTv nextFrag = new IpTv();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_layout, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

}
