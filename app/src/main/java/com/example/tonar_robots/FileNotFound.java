package com.example.tonar_robots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class FileNotFound extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_not_found);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getSupportActionBar().hide();
    }


    public void OnBack(View view) {

        Intent intent = new Intent(FileNotFound.this, PinCode_main.class);

        fio_full.changed_massiv = new ArrayList<String>();
        fio_full.str_file = new ArrayList<String>();
        fio_full.brigada = "";
        fio_full.brigada_name = "";
        startActivity(intent);


    }
}