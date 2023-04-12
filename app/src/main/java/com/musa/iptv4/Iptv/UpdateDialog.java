package com.musa.iptv4.Iptv;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.musa.iptv4.R;

public class UpdateDialog extends BottomSheetDialogFragment {

    private EditText updateTitle;
    private EditText updateUrl;
    private EditText updateIcon;
    private EditText updateAbout;
    private IpTvDBHelper dbHelper;
    private long receivedPersonId;

    Button btCancel, btnUpdate;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(R.layout.update_layout_dialog, null);

        //init
        updateTitle = view.findViewById(R.id.ip_tv_title);
        updateUrl= view.findViewById(R.id.ip_tv_url);
        updateIcon= view.findViewById(R.id.ip_tv_icon);
        updateAbout = view.findViewById(R.id.ip_tv_about);

        btnUpdate = view.findViewById(R.id.bts_ok);
        btCancel = view.findViewById(R.id.bts_cancel);

        dbHelper = new IpTvDBHelper(getContext());
        try {
            //get intent to get person id
            receivedPersonId = getArguments().getLong("USER_ID");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Imodel queriedImodel = dbHelper.getPerson(receivedPersonId);
        //set field to this user data
        updateTitle.setText(queriedImodel.getiTitle());
        updateUrl.setText(queriedImodel.getiUrl());
        updateAbout.setText(queriedImodel.getiAbout());
        updateIcon.setText(queriedImodel.getImage());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePerson();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }
    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }
    private void updatePerson(){
        String iTitle = updateTitle.getText().toString().trim();
        String iAbout = updateAbout.getText().toString().trim();
        String iUrl = updateUrl.getText().toString().trim();
        String image = updateIcon.getText().toString().trim();

        if (iTitle.trim().equals("") || iTitle.isEmpty()) {
            iTitle = null;
            updateTitle.requestFocus();
            //error name is empty
            Toast.makeText(getContext(), "You must enter a name", Toast.LENGTH_SHORT).show();

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
        dismiss();

    }

    private void goBackHome(){
        IptvFragment nextFrag= new IptvFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

}

