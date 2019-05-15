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

import com.musa.iptv4.R;

import androidx.appcompat.app.AppCompatDialogFragment;


public class RecordDialog extends AppCompatDialogFragment {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mOccupationEditText;
    private EditText mImageEditText;

    private IpTvDBHelper dbHelper;




    private EditText name, stream, image;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {






        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_layout_dialog, null);

        builder.setView(view)
                .setTitle("Add a new Stream")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePerson();


                    }
                });

        //name = view.findViewById(R.id.name);
        //stream = view.findViewById(R.id.stream);
        //image = view.findViewById(R.id.image);
        //init
        mNameEditText = view.findViewById(R.id.ip_tv_title);
        mAgeEditText = view.findViewById(R.id.ip_tv_url);
        mOccupationEditText = view.findViewById(R.id.ip_tv_about);
        mImageEditText = view.findViewById(R.id.ip_tv_icon);


        return builder.create();

    }

    private void savePerson(){
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String occupation = mOccupationEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();
        dbHelper = new IpTvDBHelper(getContext());

        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(getContext(), "You must enter a name", Toast.LENGTH_SHORT).show();
        }


        if(age.isEmpty()){
            //error name is empty
            Toast.makeText(getContext(), "You must enter an age", Toast.LENGTH_SHORT).show();
        }

        if(occupation.isEmpty()){
            //error name is empty
            Toast.makeText(getContext(), "You must enter an occupation", Toast.LENGTH_SHORT).show();
        }

        if(image.isEmpty()){
            //error name is empty
            Toast.makeText(getContext(), "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        //create new iModel
        iModel iModel = new iModel(name, age, occupation, image);
        dbHelper.saveNewPerson(iModel);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(getContext(), IpTv.class));
    }
}
