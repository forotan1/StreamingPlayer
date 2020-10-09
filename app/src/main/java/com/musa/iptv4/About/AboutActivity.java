package com.musa.iptv4.About;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.DarkModeHelper;
import com.musa.iptv4.Utilities.SharedPref;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import static com.musa.iptv4.R.drawable.abc_ic_voice_search_api_material;
import static com.musa.iptv4.R.drawable.day_icon;
import static com.musa.iptv4.R.drawable.night_icon;


public class AboutActivity extends Fragment {
    TextView changeLang;
    Switch mySwitch;
    SharedPref sharedPref;
    ImageView shareApp;


    private static ImageButton changeMode;
    Context context;

    private RadioGroup rg;
    private RadioButton radioButton;

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_about, container, false);


        changeMode = view.findViewById(R.id.change_mode);


        sharedPref = new SharedPref(getContext());

        switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())){
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                changeMode.setBackground(getResources().getDrawable(night_icon));

                break;
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                changeMode.setBackground(getResources().getDrawable(day_icon));
//                Intent intent2 = new Intent(getActivity(), AboutActivity.class);
//                startActivity(intent2);
                break;
            default:
                if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    getActivity().setContentView(R.layout.activity_about);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
//                Intent intent3 = new Intent(getActivity(), AboutActivity.class);
//                startActivity(intent3);

        }
        context = getContext();

        changeMode = view.findViewById(R.id.change_mode);
        changeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_layout);
                dialog.setTitle("Choose the mode");

                rg = dialog.findViewById(R.id.radioGroupTheme);
                switch (DarkModeHelper.getInstance().getPref(getActivity().getBaseContext())) {
                    case "dark":
                        rg.check(R.id.radioButtonDark);

                        break;
                    case "light":
                        rg.check(R.id.radioButtonLight);
                        break;
                    default:
                        rg.check(R.id.radioButtonDefault);
                }

                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        switch (i) {
                            case R.id.radioButtonDark:
                                DarkModeHelper.getInstance().setPref("dark", getActivity().getBaseContext());
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                                Intent intent1 = new Intent(getActivity(), AboutActivity.class);
//                                startActivity(intent1);
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
                                    getActivity().setContentView(R.layout.activity_about);
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
//
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
//        changeLang = view.findViewById(R.id.change_lang);
//        changeLang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showLangChangeDialo();
//
//            }
//        });
//
//        loadLocale();



        return view;

    }



//    private void showLangChangeDialo() {
//        final  String[] listItem ={"English", "Deutsch", "پارسی"};
//        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AboutActivity.this);
//        mBuilder.setTitle("Chnage Language");
//        mBuilder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                if (i == 0){
//                    changeLang("en");
//                    recreate();
//                }
//                if (i == 1){
//                    changeLang("de");
//                    recreate();
//                }
//                if (i == 2){
//                    changeLang("fa");
//                    recreate();
//                }
//
//                dialog.dismiss();
//            }
//        });
//        AlertDialog mdialog = mBuilder.create();
//        mdialog.show();
//    }
//
//    public void loadLocale(){
//        String langPref = "language";
//        SharedPreferences preferences = getSharedPreferences("com.musa.nightmode.PREFRENCES", Context.MODE_PRIVATE);
//        String language = preferences.getString(langPref,"");
//        Locale locale = new Locale(language);
//        Locale.setDefault(locale);
//        Configuration configuration = new Configuration();
//
//        configuration.locale = locale;
//        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
//
//    }
//
//    private void changeLang(String lang) {
//        if (lang.equalsIgnoreCase(""))
//            return;
//
//        Locale locale = new Locale(lang);
//        savelocale(lang);
//        Locale.setDefault(locale);
//        Configuration configuration = new Configuration();
//
//        configuration.locale = locale;
//        getBaseContext().getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
//
//    }
//
//    public void savelocale (String lang){
//        String langPref = "language";
//        SharedPreferences prefs = getSharedPreferences("com.musa.nightmode.PREFRENCES", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(langPref, lang);
//        editor.apply();
//    }







}
