package com.musa.iptv4.Iptv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.musa.iptv4.R;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateRecordActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mOccupationEditText;
    private EditText mImageEditText;
    private Button mUpdateBtn;

    private IpTvDBHelper dbHelper;
    private long receivedPersonId;

    private static final String TAG = "UpdateRecordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);

        //init
        mNameEditText = (EditText)findViewById(R.id.userNameUpdate);
        mAgeEditText = (EditText)findViewById(R.id.userAgeUpdate);
        mOccupationEditText = (EditText)findViewById(R.id.userOccupationUpdate);
        mImageEditText = (EditText)findViewById(R.id.userProfileImageLinkUpdate);
        mUpdateBtn = (Button)findViewById(R.id.updateUserButton);

        dbHelper = new IpTvDBHelper(this);

        try {
            //get intent to get person id
            receivedPersonId = getIntent().getLongExtra("USER_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        iModel queriedIModel = dbHelper.getPerson(receivedPersonId);
        //set field to this user data
        mNameEditText.setText(queriedIModel.getiTitle());
        mAgeEditText.setText(queriedIModel.getiUrl());
        mOccupationEditText.setText(queriedIModel.getiAbout());
        mImageEditText.setText(queriedIModel.getImage());



        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updatePerson();
            }
        });





    }

    private void updatePerson(){
        Log.d(TAG, "updatePerson: update person");
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String occupation = mOccupationEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();


        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(age.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an age", Toast.LENGTH_SHORT).show();
        }

        if(occupation.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an occupation", Toast.LENGTH_SHORT).show();
        }

        if(image.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        //create updated person
        iModel updatedIModel = new iModel(name, age, occupation, image);

        //call dbhelper update
        dbHelper.updatePersonRecord(receivedPersonId, this, updatedIModel);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(this, IpTv.class));
    }
}
