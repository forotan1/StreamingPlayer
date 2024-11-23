package com.musa.iptv4.About;

import static android.app.Activity.RESULT_OK;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.daimajia.numberprogressbar.BuildConfig;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.DarkModeHelper;
import com.musa.iptv4.Utilities.LocaleHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.musa.iptv4.Utilities.librariesLicenses;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import timber.log.Timber;


public class ExtraFragment extends Fragment {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String PROFILE_IMAGE_PATH = "profile_image_path";
    private static final int PICK_IMAGE_REQUEST = 1;
    ConstraintLayout changeLang, changeMode, share_app, weblinks;
    TextView theme_name, language_name, user_name, version_text;
    private Context mContext;
    private RadioGroup themeGroup;
    SharedPreferences saveName, saveProfilePic;
    ImageView profilePic, faceBookIcon, telegramIcon, youTubeIcon, tiktokIcon, theme_ic;

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
        String versionName = BuildConfig.VERSION_NAME;
        String versionNameText = (getString(R.string.app_version));
        version_text.setText(versionNameText +" : " + versionName);


        profilePic = view.findViewById(R.id.profile_pic);
        faceBookIcon = view.findViewById(R.id.facebook_icon);
        telegramIcon = view.findViewById(R.id.telegram_icon);
        youTubeIcon = view.findViewById(R.id.youTube_icon);
        tiktokIcon = view.findViewById(R.id.tiktok_icon);
        theme_ic = view.findViewById(R.id.theme_icon);

        socialMediaLinks();

        saveProfilePic = requireActivity().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

        String imagePath = saveProfilePic.getString(PROFILE_IMAGE_PATH, null );
        if (imagePath != null){
            Bitmap profileImage = BitmapFactory.decodeFile(imagePath);
            profilePic.setImageBitmap(profileImage);
        }

        profilePic.setOnClickListener(view12 -> loadImageFromGallery());


        saveName = requireActivity().getSharedPreferences("User_name",Context.MODE_PRIVATE);
        user_name = view.findViewById(R.id.user_name_text);

        user_name.setOnClickListener(view13 -> {

            EditText editText = new EditText(requireActivity().getApplicationContext());
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.write_name));
            builder.setView(editText);
            builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                String name = editText.getText().toString().trim();
                if (!name.isEmpty()){
                    SharedPreferences.Editor editor = saveName.edit();
                    editor.putString("name", name);
                    editor.apply();


                }


            });
            builder.setNegativeButton(getString(R.string.cancel), null);
            builder.show();

        });


        String savedText = saveName.getString("name", getString(R.string.write_again));
        user_name.setText(savedText);
        switch (DarkModeHelper.getInstance().getPref(requireActivity().getBaseContext())){
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                theme_ic.setBackgroundResource(R.drawable.dark_ic);
                theme_name.setText(R.string.dark);


                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                theme_ic.setBackgroundResource(R.drawable.light_ic);
                theme_name.setText(R.string.light);


                break;
            default:
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    theme_ic.setBackgroundResource(R.drawable.dark_ic);
                    theme_name.setText(R.string.system_default);

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }

        }


        changeMode.setOnClickListener(v -> {

            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.change_theme_layout);
            themeGroup = dialog.findViewById(R.id.radioGroupTheme);
            switch (DarkModeHelper.getInstance().getPref(requireActivity().getBaseContext())) {
                case "dark":
                    themeGroup.check(R.id.radioButtonDark);
                    break;
                case "light":
                    themeGroup.check(R.id.radioButtonLight);
                    break;
                default:
                    themeGroup.check(R.id.radioButtonDefault);
            }

            themeGroup.setOnCheckedChangeListener((radioGroup, i) -> {


                if (i == R.id.radioButtonDark) {
                    DarkModeHelper.getInstance().setPref("dark", requireActivity().getBaseContext());
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    dialog.dismiss();
                }
                    else if (i == R.id.radioButtonLight) {
                    DarkModeHelper.getInstance().setPref("light", requireActivity().getBaseContext());
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    dialog.dismiss();
                }
                    else if (i == R.id.radioButtonDefault) {
                        DarkModeHelper.getInstance().setPref("default", requireActivity().getBaseContext());

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            requireActivity().setContentView(R.layout.fragment_extra);
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                        }
                        dialog.dismiss();

                }


            });


            dialog.show();


        });


        share_app = view.findViewById(R.id.share_layout);
        share_app.setOnClickListener(v -> {
            Intent shaIntent = new Intent(Intent.ACTION_SEND);
            String sharedBody = "Download the App from this link: https://forotan1.github.io/";
            shaIntent.setType("text/plain");
            shaIntent.putExtra(Intent.EXTRA_SUBJECT, "Download the App");
            shaIntent.putExtra(Intent.EXTRA_TEXT, sharedBody);
            startActivity(Intent.createChooser(shaIntent, "share via"));
        });

        weblinks = view.findViewById(R.id.open_source_link);
        weblinks.setOnClickListener(view1 -> {

            Intent link = new Intent(getContext(), librariesLicenses.class);
            startActivity(link);
        });


        changeLang = view.findViewById(R.id.language_layout);
        changeLang.setOnClickListener(v -> changeLanguage());

        return view;

    }

    private void socialMediaLinks() {
        faceBookIcon.setOnClickListener(v -> openSocialMediaProfile("facebook", "https://www.facebook.com/musa.forotan"));


        youTubeIcon.setOnClickListener(v -> openSocialMediaProfile("youtube", "https://youtube.com/@TheMusaTalk"));

        tiktokIcon.setOnClickListener(v -> openSocialMediaProfile("tiktok", "https://www.tiktok.com/@musaforotan"));

        telegramIcon.setOnClickListener(v -> openSocialMediaProfile("telegram", "https://t.me/forotan01"));
    }

    private void openSocialMediaProfile(String socialMedia, String profileUrl) {
        String packageName;
        switch (socialMedia) {
            case "facebook":
                packageName = "com.facebook.katana";
                break;
            case "youtube":
                packageName = "com.google.android.youtube";
                break;
            case "tiktok":
                packageName = "com.zhiliaoapp.musically";
                break;
            case "telegram":
                packageName = "org.telegram.messenger";
                break;
            default:
                return;
        }

        // Check if the app is installed
        Intent intent;
        intent = requireActivity().getPackageManager().getLaunchIntentForPackage(packageName);
        // If the app is installed, open the social media profile in the app
        // If the app is not installed, open the social media profile in the web browser
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            startActivity(Objects.requireNonNullElseGet(intent, () -> new Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl))));
        }
    }

    private void changeLanguage(){

        final Dialog langDialog = new Dialog(mContext);
        langDialog.setContentView(R.layout.lang_custom_layout);
        RadioGroup languageRadioGroup = langDialog.findViewById(R.id.radioGroupLang);
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
        languageRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {


                if ( i==R.id.radioButtonEnglish) {
                    LocaleHelper.setLocale(mContext, "en");
                    langDialog.dismiss();
                    Objects.requireNonNull(requireActivity()).recreate();
                }
                else if ( i== R.id.radioButtonPersian) {
                    LocaleHelper.setLocale(mContext, "fa");
                    langDialog.dismiss();
                    Objects.requireNonNull(requireActivity()).recreate();
                }
               else if ( i== R.id.radioButtonDeutsch) {
                    LocaleHelper.setLocale(mContext,"de");
                    langDialog.dismiss();
                    Objects.requireNonNull(requireActivity()).recreate();
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
            Timber.e(e, "Error while loading image from gallery");
        }

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    Bitmap selectedImage = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                    profilePic.setImageBitmap(selectedImage);

                    // Save the new profile image path
                    String imagePath = saveProfileImage(selectedImage);
                    saveProfileImagePath(imagePath);
                } catch (IOException e) {
                    Timber.e(e, "Error while loading image from gallery");
                }
            }
        }


    }

    private String saveProfileImage(Bitmap image) {
        String imagePath = Objects.requireNonNull(requireActivity()).getExternalFilesDir(null) + File.separator + "profile_image.png";
        try {
            FileOutputStream fos = new FileOutputStream(imagePath);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            Timber.e(e, "Error while saving profile image");
        }
        return imagePath;
    }

    private void saveProfileImagePath(String imagePath) {
        SharedPreferences.Editor editor = saveProfilePic.edit();
        editor.putString(PROFILE_IMAGE_PATH, imagePath);
        editor.apply();
    }



}
