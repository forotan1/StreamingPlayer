package com.musa.iptv4.About;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.musa.iptv4.R;

import java.util.Timer;
import java.util.TimerTask;

public class Loading_activity extends AppCompatActivity implements OnProgressBarListener {

    private Timer timer;
    private NumberProgressBar bnp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_activity);

        bnp = (NumberProgressBar) findViewById(R.id.numberbar6);
        bnp.setOnProgressBarListener(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        bnp.incrementProgressBy(1);
                    }
                });
            }
        }, 100, 40);
    }

    @Override
    public void onProgressChange(int current, int max) {
        if (current == max) {
            startActivity(new Intent(Loading_activity.this, AboutActivity.class));
            finish();

            bnp.setProgress(0);



        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        finish();
    }
}

