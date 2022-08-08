package com.gmail.mateendev3.stopwatchcwm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean isRunning = false;
    private long pauseOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Chronometer chronometer = findViewById(R.id.custom_chronometer);
        Button btnStart = findViewById(R.id.btn_start);
        Button btnPause = findViewById(R.id.btn_pause);
        Button btnReset = findViewById(R.id.btn_reset);

        chronometer.setFormat("Timer: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10_000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    Toast.makeText(MainActivity.this, "10 sec completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    isRunning = true;
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    isRunning = false;
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.stop();
                pauseOffset = 0;
                isRunning = false;
            }
        });
    }
}