package com.example.tonar_robots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
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

public class Load_Content extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String LOG_TAG = Load_Content.class.getSimpleName();
        fio_full.thread_run=false;
        setContentView(R.layout.activity_load__content);

        try {
            saveUrl(Environment.getExternalStorageDirectory() + "/DCIM/"+""+fio_full.brigada+".txt", "http://10.0.1.253/plan_proizvodstva/"+fio_full.brigada+".txt");
            Log.d(LOG_TAG, "Pin ссылка: " + "http://10.0.1.253/plan_proizvodstva/"+fio_full.brigada+".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public  void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;


        try {
            fio_full.str_file = new ArrayList<String>();
            fio_full.changed_massiv = new ArrayList<String>();
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);
            String LOG_TAG = Plan.class.getSimpleName();

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
                Log.d(LOG_TAG, "строка :  "+"пишет ваще че?");


            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;



            //startActivity(intent);


        } catch (IOException e) {
            e.printStackTrace();
        }  finally {

            if (fout != null) {
                fout.close();


                try {
                    File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/"+fio_full.brigada+".txt");
                    //создаем объект FileReader для объекта File
                    FileReader fr = new FileReader(file);
                    //создаем BufferedReader с существующего FileReader для построчного считывания
                    BufferedReader reader = new BufferedReader(fr);
                    // считаем сначала первую строку
                    String line = reader.readLine();
                    while (line != null) {


                        String[] parts_file = line.split(";");
                        Date currentDate = new Date();


                        // Форматирование времени как "часы:минуты:секунды"
                        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String timeText = timeFormat.format(currentDate);




                        String time = parts_file[3];






                        Date data_file= timeFormat.parse(time);

                        Date data_now= timeFormat.parse(timeText);
                        if (data_file.after(data_now)&&!fio_full.sdelano.contains(line)) {

                            fio_full.str_file.add(line);
                            Log.d("строки",line);

                            fio_full.changed_massiv.add(line);


                        }

                        // считываем остальные строки в цикле
                        line = reader.readLine();


                    }
                    Intent intent = new Intent(Load_Content.this, Plan.class);
                    startActivity(intent);
                } catch (FileNotFoundException | ParseException e) {
                    e.printStackTrace();

                }

            }
        }
    }
}