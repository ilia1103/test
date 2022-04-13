package com.example.tonar_robots;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

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

public class PinCode_main extends AppCompatActivity {
    public static final String TAG = "PinLockView";
    String LOG_TAG = PinCode_main.class.getSimpleName();


    private PinLockView mPinLockView;
    private IndicatorDots mIndicatorDots;

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {


            int sch_for_passes = 0;
            while (sch_for_passes<fio_full.massiv_passwords.size()){
                String [] need_str = fio_full.massiv_passwords.get(sch_for_passes).split("_");
                String pass = need_str[1];
                String brigada = need_str[0];




                if (pin.equals(pass)){
                    fio_full.brigada = need_str[0];
                    fio_full.brigada_name = need_str[2];




                    final String LOG_TAG = "myLogs";
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        return;
                    }
                    // получаем путь к SD


                    ///ЭТИ 2 СТРОЧКИ ВАЖНЫ
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                    StrictMode.setThreadPolicy(policy);

                    try{
                        //saveUrl(Environment.getExternalStorageDirectory() + "/DCIM/"+brigada+".txt", "http://10.0.1.253/plan_proizvodstva/"+brigada+".txt");
                        saveUrl(getExternalFilesDir(Environment.DIRECTORY_DCIM) +"/"+brigada+".txt", "http://10.0.1.253/plan_proizvodstva/"+brigada+".txt");
                        Log.d(TAG, "Pin ссылка: " + "http://10.0.1.253/plan_proizvodstva/"+brigada+".txt");

                        fio_full.ssilkaFile = "http://10.0.1.253/plan_proizvodstva/"+brigada+".txt";


                    }
                    catch(Exception e){
                        Log.d(TAG, "Ошибка сохранения " + pin);

                    }



                }




                sch_for_passes = sch_for_passes + 1;

            }


            Log.d(TAG, "Pin complete: " + pin);
            TextView label_pin = (TextView)findViewById(R.id.profile_name);
            String stroka = (String) label_pin.getText();


        }

        @Override
        public void onEmpty() {
            Log.d(TAG, "Pin empty");
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin_code_main);




        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);
        mPinLockView.setPinLockListener(mPinLockListener);
        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        mPinLockView.setPinLength(4);
        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }
    public void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {


        FileOutputStream fout = null;
        try (BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream())) {
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
                Log.d(LOG_TAG, "строка :  " + count);


            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();


        }
        finally {

            if (fout != null) {
                fout.close();
                Intent intent = new Intent(PinCode_main.this, MainActivity.class);


                try {

                    //File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/"+fio_full.brigada+".txt");
                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_DCIM) +"/"+fio_full.brigada+".txt");
                    //создаем объект FileReader для объекта File
                    FileReader fr = new FileReader(file);
                    //создаем BufferedReader с существующего FileReader для построчного считывания
                    BufferedReader reader = new BufferedReader(fr);
                    // считаем сначала первую строку
                    String line = reader.readLine();
                    while (line != null) {
                        fio_full.str_file.add(line);
                        fio_full.changed_massiv.add(line);
                        // считываем остальные строки в цикле
                        line = reader.readLine();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();




            }
                if (fio_full.changed_massiv.size()<2){
                    Intent intentNull = new Intent(PinCode_main.this, FileNotFound.class);

                    startActivity(intentNull);
                }
                else{
                    startActivity(intent);
                }

        }
        }

    }
}