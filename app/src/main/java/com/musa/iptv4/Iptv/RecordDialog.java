package com.musa.iptv4.Iptv;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.musa.iptv4.R;

public class RecordDialog extends BottomSheetDialogFragment {


    Button btHelp, btOk, btCancel;
    private Activity activity;
    int theme;

    private EditText mNameEditText;
    private EditText mAgeEditText;
    private EditText mOccupationEditText;
    private EditText mImageEditText;

    private IpTvDBHelper dbHelper;

    public RecordDialog(FragmentActivity activity, int bottomSheetDialogTheme) {
        this.activity = activity;
        this.theme = bottomSheetDialogTheme;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_layout_dialog, container, false);

        btOk = v.findViewById(R.id.bts_ok);
        btCancel = v.findViewById(R.id.bts_cancel);
        btHelp = v.findViewById(R.id.bts_hlep);

        mNameEditText = v.findViewById(R.id.ip_tv_title);
        mAgeEditText = v.findViewById(R.id.ip_tv_url);
        mOccupationEditText = v.findViewById(R.id.ip_tv_about);
        mImageEditText = v.findViewById(R.id.ip_tv_icon);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePerson();
                dismiss();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btHelp.setOnClickListener(view -> Toast.makeText(getActivity(), "help pressed", Toast.LENGTH_LONG).show());

        return v;
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    private void savePerson() {
        String name = mNameEditText.getText().toString().trim();
        String age = mAgeEditText.getText().toString().trim();
        String occupation = mOccupationEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();

        dbHelper = new IpTvDBHelper(getContext());

        if (name.trim().equals("") || name.isEmpty()) {
            name = null;
            mNameEditText.requestFocus();
            //error name is empty
            Toast.makeText(getContext(), "You must enter a name", Toast.LENGTH_SHORT).show();

        } else {
        }
        if (age.isEmpty()) {
            //error name is empty
            Toast.makeText(getContext(), "You must enter an age", Toast.LENGTH_SHORT).show();
        }

        if (occupation.isEmpty()) {
            //error name is empty
            Toast.makeText(getContext(), "You must enter an occupation", Toast.LENGTH_SHORT).show();
        }

        if (image.isEmpty()) {
            //error name is empty
            Toast.makeText(getContext(), "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        //create new Imodel
        Imodel iModel = new Imodel(name, age, occupation, image);
        dbHelper.saveNewPerson(iModel);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome() {
        IptvFragment nextFrag = new IptvFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }
}
