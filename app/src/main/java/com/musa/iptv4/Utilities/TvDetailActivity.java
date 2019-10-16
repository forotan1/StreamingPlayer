package com.musa.iptv4.Utilities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.jarvanmo.exoplayerview.media.ExoMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.media.SimpleQuality;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.musa.iptv4.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_LANDSCAPE;
import static com.jarvanmo.exoplayerview.orientation.OnOrientationChangedListener.SENSOR_PORTRAIT;
import static com.musa.iptv4.LiveTv.Afghanistan.AfghanTab.EXTRA_ABOUT_TV;
import static com.musa.iptv4.LiveTv.Afghanistan.AfghanTab.EXTRA_ICON;
import static com.musa.iptv4.LiveTv.Afghanistan.AfghanTab.EXTRA_LIVE_URL;
import static com.musa.iptv4.LiveTv.Afghanistan.AfghanTab.EXTRA_TITLE;

public class TvDetailActivity extends AppCompatActivity {

    private ExoVideoView videoView;

    CircularImageView circularImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

            Intent intent = getIntent();
            String tvTitle= intent.getStringExtra(EXTRA_TITLE);
            String tvIcon = intent.getStringExtra(EXTRA_ICON);
            String aboutTv = intent.getStringExtra(EXTRA_ABOUT_TV);
            String liveUrl = intent.getStringExtra(EXTRA_LIVE_URL);


        TextView titleView = findViewById(R.id.detailA_tv_title);
        TextView tvInfo = findViewById(R.id.tv_info);

        circularImageView = findViewById(R.id.about_profile_pic);

        titleView.setText(tvTitle);
        tvInfo.setText(aboutTv);

        Glide.with(this).load(tvIcon).into(circularImageView);

        videoView = findViewById(R.id.ExovideoView);


        videoView.setBackListener((view, isPortrait) -> {
            if (isPortrait) {
                finish();
            }
            return false;
        });



        videoView.setOrientationListener(orientation -> {
            if (orientation == SENSOR_PORTRAIT) {
                changeToPortrait();
            } else if (orientation == SENSOR_LANDSCAPE) {
                changeToLandscape();
            }
        });



        //String url = getIntent().getStringExtra("key_url");

        final SimpleMediaSource mediaSource = new SimpleMediaSource(liveUrl);

        mediaSource.setDisplayName("VideoPlaying");

        List<ExoMediaSource.Quality> qualities = new ArrayList<>();
        ExoMediaSource.Quality quality;

        for (int i = 0; i < 6; i++) {
            SpannableString spannableString = new SpannableString("Quality" + i);
            if (i % 2 == 0) {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.YELLOW);
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            } else {
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.RED);
                spannableString.setSpan(colorSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }

            quality = new SimpleQuality(spannableString, mediaSource.uri());
            qualities.add(quality);
        }
        mediaSource.setQualities(qualities);



        videoView.play(mediaSource);

    }


    private void changeToPortrait() {

        WindowManager.LayoutParams attr = getWindow().getAttributes();
        attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        window.setAttributes(attr);
        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }


    private void changeToLandscape() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = getWindow();
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }




    @Override
    protected void onStart() {
        super.onStart();
        if ( Build.VERSION.SDK_INT > 23) {
            videoView.resume();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (( Build.VERSION.SDK_INT <= 23)) {
            videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if ( Build.VERSION.SDK_INT <= 23) {
            videoView.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if ( Build.VERSION.SDK_INT > 23) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.releasePlayer();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return videoView.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

}
