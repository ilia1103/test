package com.example.tonar_robots;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class God_mode extends AppCompatActivity {

    String LOG_TAG = PinCode_main.class.getSimpleName();

    public static final String TAG = "GodMode";



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Objects.requireNonNull(getSupportActionBar()).hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_god_mode);


        fio_full.changed_massiv = new ArrayList<>();

        try{
            //saveUrl(Environment.getExternalStorageDirectory() + "/DCIM/"+brigada+".txt", "http://10.0.1.253/plan_proizvodstva/"+brigada+".txt");
            saveUrl(getExternalFilesDir(Environment.DIRECTORY_DCIM) +"/"+fio_full.brigada+".txt", "http://10.0.1.253/plan_proizvodstva/"+fio_full.brigada+".txt");
            Log.d(TAG, "Pin ссылка: " + "http://10.0.1.253/plan_proizvodstva/"+fio_full.brigada+".txt");

            fio_full.ssilkaFile = "http://10.0.1.253/plan_proizvodstva/"+fio_full.brigada+".txt";


        }
        catch(Exception e){
            Log.d(TAG, "Ошибка сохранения " );

        }




    }
    public void OnStart(View view) {

        Intent intent = new Intent(God_mode.this, MainActivity.class);

        startActivity(intent);


    }



    public void saveUrl(final String filename, final String urlString)
            throws IOException {


        FileOutputStream fout = null;
        try (BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream())) {
            fout = new FileOutputStream(filename);

            final byte[] data = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
                Log.d(LOG_TAG, "строка :  " + count);


            }


        }
        finally {

            if (fout != null) {
                fout.close();
                Intent intent = new Intent(God_mode.this, MainActivity.class);


                try {
                    Log.d(LOG_TAG, "уже был в сделано :  "+fio_full.sdelano);
                    //File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/"+fio_full.brigada+".txt");
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_DCIM) +"/"+fio_full.brigada+".txt");
                    //создаем объект FileReader для объекта File
                    FileReader fr = new FileReader(file);
                    //создаем BufferedReader с существующего FileReader для построчного считывания
                    BufferedReader reader = new BufferedReader(fr);
                    // считаем сначала первую строку
                    String line = reader.readLine();
                    String mainStr = "";
                    String newStrLine="";
                    while (line != null) {


                        //считывать все количества по каждому вхождению, если вхождений больше 1, сравнивать с новым количеством, если равно - не добавлять, ниже дописать второе условие по этому, просчитать все количества, забить в переменную, сравнить при добавлении
                        int sch = 0;
                        int manyOtm = 0;
                        int manyTime = 0;
                        boolean vipFull = false;
                        while (sch<fio_full.sdelano.size()){
                            String[] newStrParts = fio_full.sdelano.get(sch).split(";");


                            int kolvoSdelano = Integer.parseInt(newStrParts[5]);

                            int timeSdelano = Integer.parseInt(newStrParts[2]);
                            Log.d(LOG_TAG, "где поломка 1 :  "+fio_full.sdelano);

                            //String newStr = newStrParts[0]+";"+newStrParts[1]+";"+newStrParts[2]+";"+newStrParts[3]+";"+newStrParts[4];
                            String newStr = newStrParts[0]+";"+newStrParts[1]+";"+newStrParts[3]+";"+newStrParts[4];

                            String[] lineParts = line.split(";");

                            //что то непонятно по этой переменной
                            mainStr = lineParts[0]+";"+lineParts[1]+";"+lineParts[2]+";"+lineParts[3]+";"+lineParts[4]+";"+lineParts[5];


                            int kolvoFile = Integer.parseInt(lineParts[5]);

                            int timeFile = Integer.parseInt(lineParts[2]);

                            newStrLine = lineParts[0]+";"+lineParts[1]+";"+lineParts[3]+";"+lineParts[4];
                            if (newStrLine.equals(newStr)&&kolvoFile>kolvoSdelano){

                                int ostatok;
                                int ostatokTime;

                                if (manyOtm>0){



                                    String[] many = newStrLine.split(";");



                                    ostatokTime = timeFile - timeSdelano - manyTime;


                                    String mainStrKolvo = many[0]+";"+many[1]+";"+ ostatokTime +";"+many[2]+";"+many[3];
                                    Log.d(LOG_TAG, "где поломка внутри manyOtm :  "+timeSdelano);

                                    ostatok = kolvoFile - kolvoSdelano - manyOtm;
                                    newStrLine = mainStrKolvo+";"+ ostatok;


                                    Log.d(LOG_TAG, "пишет или не пишет то :  "+newStrLine+";"+ ostatok);
                                }
                                else {

                                    ostatokTime = timeFile - timeSdelano;

                                    ostatok = kolvoFile - kolvoSdelano;
                                    String[] many2 = newStrLine.split(";");


                                    Log.d(LOG_TAG, "где поломка 2 :  " + timeSdelano);
                                    newStrLine = many2[0]+";"+many2[1]+";"+ ostatokTime +";"+many2[2]+";"+many2[3]+";"+ ostatok;


                                }

                                manyOtm = manyOtm + kolvoSdelano;
                                manyTime = manyTime + timeSdelano;
                                Log.d(LOG_TAG, "где поломка 2 :  "+timeSdelano);

                            }
                            else{
                                newStrLine = line;
                            }

                            sch = sch + 1;
                        }
                        if (newStrLine.equals("")){
                            newStrLine = line;
                        }
                        int schVip = 0;
                        while (schVip<fio_full.sdelano.size()){
                            String[] parts = fio_full.sdelano.get(schVip).split(";");

                            String myStr = parts[0]+";"+parts[1]+";"+parts[2]+";"+parts[3]+";"+parts[4]+";"+parts[5];
                            // Log.d(LOG_TAG, "такие строю из сделано :  "+myStr);

                            Log.d(LOG_TAG, "строка главнаяСтрока :  " + mainStr);
                            Log.d(LOG_TAG, "строка изСделано :  " + myStr);
                            if (myStr.equals(mainStr)){

                                vipFull = true;
                            }

                            schVip = schVip+1;
                        }

                        if (vipFull){

                            Log.d(LOG_TAG, "уже был в сделано :  ");
                        }
                        else{
                            String[] partsZero = newStrLine.split(";");

                            if (Integer.parseInt(partsZero[5])<1){
                                Log.d(LOG_TAG, "по отметкам уже выполнен(несколько) :  ");
                            }
                            else{
                                fio_full.str_file.add(line);
                                fio_full.changed_massiv.add(newStrLine);
                            }

                        }

                        // считываем остальные строки в цикле
                        newStrLine = "";
                        mainStr = "";
                        line = reader.readLine();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();




                }
                if (fio_full.changed_massiv.size()<1){
                    Intent intentNull = new Intent(God_mode.this, FileNotFound.class);

                    startActivity(intentNull);
                }
                else{
                    startActivity(intent);
                }

            }
        }

    }

}
