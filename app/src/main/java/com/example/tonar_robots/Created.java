package com.example.tonar_robots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.nio.charset.StandardCharsets;

public class Created extends AppCompatActivity {
    private TextView timerValue;
    private long startTime = 1000L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_created);
        String LOG_TAG = Created.class.getSimpleName();
        timerValue = (TextView) findViewById(R.id.tv_timer_value);

        Button startButton = (Button) findViewById(R.id.btn_start);
        Button pauseButton = (Button) findViewById(R.id.btn_pause);
        Button stopButton = (Button) findViewById(R.id.btn_stop);
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
        stopButton.setOnClickListener(v -> {
            startTime = 0L;
            customHandler.removeCallbacks(updateTimerThread);
            timerValue.setText(("Прервано оператором"));
            Toast.makeText(Created.this, "Прервано оператором",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Created.this, Load_Content.class);
            startActivity(intent);

            });
    }


    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
                break;
            case R.id.btn_pause:
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                break;
            case R.id.btn_stop:

        }
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = fio_full.promejutok_need - (timeSwapBuff + timeInMilliseconds);
            if (updatedTime < 1){
                timerValue.setText("Все!");

                customHandler.removeCallbacks(updateTimerThread);
                Intent intent = new Intent(Created.this, Plan.class);
                startActivity(intent);

            }
            else {
                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (updatedTime % 1000);
                timerValue.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
                customHandler.postDelayed(this, 0);
            }

        }
    };


}