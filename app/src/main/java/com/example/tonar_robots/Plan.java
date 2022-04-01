package com.example.tonar_robots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Plan extends AppCompatActivity {

    String LOG_TAG = Plan.class.getSimpleName();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);





        fio_full.thread_run = true;





        RecyclerView recyclerView;
        TextView text = (TextView)findViewById(R.id.textView);
        text.setText(fio_full.brigada_name);

        adapter_plan myAdapter = new adapter_plan(this);

        //recyclerView = findViewById(R.id.vazhno);
        //GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView = findViewById(R.id.recycler_plan);

        Button svoboda = (Button)findViewById(R.id.svoboda);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                // force size of viewHolder here, this will override layout_height and layout_width from xml
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                lp.width = getWidth();
                return true;
            }
        };




        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);


        // String FILENAME = Environment.getExternalStorageDirectory() + "/DCIM/"+"PominutnyGrafik.txt";

    }
    public void OnGod(View view) {

        Intent intent = new Intent(Plan.this, God_mode.class);

        startActivity(intent);


    }





}
