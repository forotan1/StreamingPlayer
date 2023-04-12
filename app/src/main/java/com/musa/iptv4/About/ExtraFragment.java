package com.musa.iptv4.About;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.DarkModeHelper;
import com.musa.iptv4.Utilities.LocaleHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import static com.musa.iptv4.R.drawable.day_icon;
import static com.musa.iptv4.R.drawable.night_icon;


public class ExtraFragment extends Fragment {
    Button changeLang;
    ImageView shareApp;
    private Context mContext;
    private ImageButton changeMode;
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
        changeMode = view.findViewById(R.id.change_mode);

        switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())){
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                changeMode.setBackground(getResources().getDrawable(night_icon));

                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                changeMode.setBackground(getResources().getDrawable(day_icon));
                break;
            default:
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    changeMode.setBackground(getResources().getDrawable(day_icon));
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
        shareApp = view.findViewById(R.id.share_app);
        shareApp.setOnClickListener(new View.OnClickListener() {
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


        changeLang = view.findViewById(R.id.change_lang);
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
                break;
            case "fa":
                languageRadioGroup.check(R.id.radioButtonPersian);
                break;
            case "de":
                languageRadioGroup.check(R.id.radioButtonDeutsch);
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
