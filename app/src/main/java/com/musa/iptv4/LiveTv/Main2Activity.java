package com.musa.iptv4.LiveTv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.musa.iptv4.R;
import com.musa.iptv4.Utilities.TvDetailActivity;

public class Main2Activity extends AppCompatActivity {
    final String linkUrl = "http://livestream.5centscdn.com/live1234/2621b29e501b445fabf227b086123b70.sdp/index.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button play = findViewById(R.id.player);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playerActivity = new Intent(Main2Activity.this, TvDetailActivity.class);
                playerActivity.putExtra("play_key",linkUrl);
                startActivity(playerActivity);
            }
        });
    }


    }

