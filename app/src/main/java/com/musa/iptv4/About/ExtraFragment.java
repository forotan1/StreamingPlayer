package com.musa.iptv4.About;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.DarkModeHelper;
import com.musa.iptv4.Utilities.LocaleHelper;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.musa.iptv4.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ExtraFragment extends Fragment {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PROFILE_IMAGE_PATH = "profile_image_path";
    private static final int PICK_IMAGE_REQUEST = 1;
    String imgDecodableString;
    ConstraintLayout changeLang, changeMode, donate, share_app, suggest;
    TextView theme_name, language_name, user_name, version_text;
    private Context mContext;
    private RadioGroup themeGroup, languageRadioGroup;
    SharedPreferences saveName, saveProfilePic;
    ImageView profilePic, faceBookImage;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extra, container, false);
        changeMode = view.findViewById(R.id.theme_layout);
        theme_name = view.findViewById(R.id.theme_name_text);
        language_name = view.findViewById(R.id.lang_name_text);
        version_text = view.findViewById(R.id.version_text);
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        String versionNameText = (getString(R.string.app_version));
        version_text.setText(versionNameText +" : " + versionName);


        profilePic = view.findViewById(R.id.profile_pic);
        saveProfilePic = requireActivity().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

        String imagePath = saveProfilePic.getString(PROFILE_IMAGE_PATH, null );
        if (imagePath != null){
            Bitmap profileImage = BitmapFactory.decodeFile(imagePath);
            profilePic.setImageBitmap(profileImage);
        }

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery();

            }
        });


        saveName = getActivity().getSharedPreferences("User_name",Context.MODE_PRIVATE);
        user_name = view.findViewById(R.id.user_name_text);

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = new EditText(getActivity().getApplicationContext());
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.write_name));
                builder.setView(editText);
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString().trim();
                        if (!name.isEmpty()){
                            SharedPreferences.Editor editor = saveName.edit();
                            editor.putString("name", name);
                            editor.apply();


                        }


                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), null);
                builder.show();

            }
        });


        String savedText = saveName.getString("name", getString(R.string.write_again));
        user_name.setText(savedText);
        switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())){
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                // changeMode.setBackground(getResources().getDrawable(night_icon));
                //theme_name.setText(getText(R.string.dark));

                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                // changeMode.setBackground(getResources().getDrawable(day_icon));
                //theme_name.setText(getText(R.string.light));
                break;
            default:
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    // changeMode.setBackground(getResources().getDrawable(day_icon));
                    // theme_name.setText(getText(R.string.system_default));
                    // getActivity().setContentView(R.layout.activity_about);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }

        }


        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.change_theme_layout);
                themeGroup = dialog.findViewById(R.id.radioGroupTheme);
                switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())) {
                    case "dark":
                        themeGroup.check(R.id.radioButtonDark);
                        break;
                    case "light":
                        themeGroup.check(R.id.radioButtonLight);
                        break;
                    default:
                        themeGroup.check(R.id.radioButtonDefault);
                }

                themeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.radioButtonDark:
                                DarkModeHelper.getInstance().setPref("dark", getActivity().getBaseContext());
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                dialog.dismiss();
                                break;
                            case R.id.radioButtonLight:
                                DarkModeHelper.getInstance().setPref("light",getActivity().getBaseContext());
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                dialog.dismiss();
                                break;
                            default:
                                DarkModeHelper.getInstance().setPref("default", getActivity().getBaseContext());

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                    getActivity().setContentView(R.layout.fragment_extra);
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                                }
                                dialog.dismiss();

                        }
                    }
                });


                dialog.show();


            }
        });
        share_app = view.findViewById(R.id.share_layout);
        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shaIntent = new Intent(Intent.ACTION_SEND);
                String sharedBody = "Download the App from this link https://farsitv.000webhostapp.com/";
                shaIntent.setType("text/plain");
                shaIntent.putExtra(Intent.EXTRA_SUBJECT, "Download the App");
                shaIntent.putExtra(Intent.EXTRA_TEXT, sharedBody);
                startActivity(Intent.createChooser(shaIntent, "share via"));
            }
        });


        changeLang = view.findViewById(R.id.language_layout);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();

            }
        });

        return view;

    }

    private void changeLanguage(){

        final Dialog langDialog = new Dialog(mContext);
        langDialog.setContentView(R.layout.lang_custom_layout);
        languageRadioGroup = langDialog.findViewById(R.id.radioGroupLang);
        String currentLanguageCode = LocaleHelper.getLanguage(getActivity());
        switch (currentLanguageCode) {

            case "en":
                languageRadioGroup.check(R.id.radioButtonEnglish);
               // language_name.setText(getText(R.string.english));
                break;
            case "fa":
                languageRadioGroup.check(R.id.radioButtonPersian);
               // language_name.setText(getText(R.string.persian));
                break;
            case "de":
                languageRadioGroup.check(R.id.radioButtonDeutsch);
               // language_name.setText(getText(R.string.german));
                break;
        }
        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.radioButtonEnglish:
                        LocaleHelper.setLocale(mContext,"en");
                        langDialog.dismiss();
                        getActivity().recreate();
                        break;
                    case R.id.radioButtonPersian:
                        LocaleHelper.setLocale(mContext,"fa");
                        langDialog.dismiss();
                        getActivity().recreate();
                        break;
                    case R.id.radioButtonDeutsch:
                        LocaleHelper.setLocale(mContext,"de");
                        langDialog.dismiss();
                        getActivity().recreate();
                        break;
                }

            }
        });
        langDialog.show();
    }

    public void loadImageFromGallery(){
        try {
            mContext = getActivity();

            Intent intentGallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, PICK_IMAGE_REQUEST);

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    profilePic.setImageBitmap(selectedImage);

                    // Save the new profile image path
                    String imagePath = saveProfileImage(selectedImage);
                    saveProfileImagePath(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private String saveProfileImage(Bitmap image) {
        String imagePath = getActivity().getExternalFilesDir(null) + File.separator + "profile_image.png";
        try {
            FileOutputStream fos = new FileOutputStream(imagePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    private void saveProfileImagePath(String imagePath) {
        SharedPreferences.Editor editor = saveProfilePic.edit();
        editor.putString(PROFILE_IMAGE_PATH, imagePath);
        editor.apply();
    }



}
