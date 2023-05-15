package com.musa.iptv4.About;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.DarkModeHelper;
import com.musa.iptv4.Utilities.LocaleHelper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.musa.iptv4.BuildConfig;



public class ExtraFragment extends Fragment {
    ConstraintLayout changeLang, changeMode, donate, share_app, suggest;
    TextView theme_name, language_name, user_name, version_text;
    private Context mContext;
    private RadioGroup themeGroup, languageRadioGroup;

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


        SharedPreferences saveName = getActivity().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        user_name = view.findViewById(R.id.user_name_text);
        String savedText = saveName.getString("name", "");
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
                        String name = editText.getText().toString();
                        user_name.setText(name);
                        SharedPreferences.Editor editor = saveName.edit();
                        editor.putString("name", name);
                        editor.apply();
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), null);
                builder.show();

            }
        });




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



}
