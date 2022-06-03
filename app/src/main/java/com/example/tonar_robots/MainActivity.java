package com.example.tonar_robots;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.tonar_robots.ui.main.SectionsPagerAdapter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.tonar_robots.fio_full.changed_massiv;
import static com.example.tonar_robots.fio_full.poisk;
import static com.example.tonar_robots.fio_full.poisk_massiv;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "добавляю в поиск" ;
    private Button openInputPopupDialogButton = null;
    // This listview is just under above button.
    private ListView userDataListView = null;
    // Below edittext and button are all exist in the popup dialog view.
    private View popupInputDialogView = null;
    // Contains user name data.
    private EditText articulEdit = null;
    // Contains password data.
    private EditText kolvoEdit = null;
    private Button raschetVipBut = null;

    private EditText timeStartEdit = null;
    private EditText timeEndEdit = null;
    private EditText timeVipEdit = null;

    // Contains email data.
    private EditText passBrigadaEdit = null;
    // Click this button in popup dialog to save user input data in above three edittext.
    private Button saveUserDataButton = null;
    // Click this button to cancel edit user data.
    private Button cancelUserDataButton = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        initMainActivityControls();
        openInputPopupDialogButton = findViewById(R.id.popupWtf);




















        Button tabelLabel = (Button)findViewById(R.id.button5);
        if (fio_full.Tabel.length()>3){
            tabelLabel.setText("Ваш табельный: "+ fio_full.Tabel);

        }
        else {
            tabelLabel.setText("Нет табельного!");
        }

    }
    public void OnGod(View view) {

        Intent intent = new Intent(view.getContext(), God_mode.class);

        startActivity(intent);


    }

    public void Tabel(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите ваш табельный");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (input.getText().toString().length()>2){

                    fio_full.Tabel = input.getText().toString();
                    Button tabelIn = findViewById(R.id.button5);
                    tabelIn.setText("Ваш Табельный: "+fio_full.Tabel);
                }
                else{

                    Toast.makeText(getBaseContext(), "Неверный формат табеля", Toast.LENGTH_SHORT).show();


                }


            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();






    }
    public void onPopup(View view) {




// Create a AlertDialog Builder.
        if (fio_full.Tabel.length()>2){



                    // Create a AlertDialog Builder.
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // Set title, icon, can not cancel properties.
                    alertDialogBuilder.setTitle("Ввод детали вне плана");
                    alertDialogBuilder.setIcon(R.drawable.worker);
                    alertDialogBuilder.setCancelable(false);
                    // Init popup dialog view and it's ui controls.
                    initPopupViewControls();
                    // Set the inflated layout view object to the AlertDialog builder.
                    alertDialogBuilder.setView(popupInputDialogView);
                    // Create AlertDialog and show.
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                    raschetVipBut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                String timeStart = timeStartEdit.getText().toString();
                                String timeEnd = timeEndEdit.getText().toString();
                                if (timeStart.length()>2 && timeEnd.length()>2){



                                    String[] parts_start = timeStart.split(":");
                                    String[] parts_end = timeEnd.split(":");

                                    int myHoursStart = Integer.parseInt(parts_start[0])*60;
                                    Log.d(LOG_TAG, "часы старт"+ myHoursStart );
                                    int myMinsStart = Integer.parseInt(parts_start[1]);

                                    int myHoursEnd = Integer.parseInt(parts_end[0])*60;
                                    Log.d(LOG_TAG, "часы end"+ myHoursEnd );
                                    int myMinsEnd = Integer.parseInt(parts_end[1]);

                                    if (myHoursEnd<myHoursStart){
                                        myHoursEnd = myHoursEnd + 24*60;


                                    }
                                    int timeDelta = (myHoursEnd + myMinsEnd) - (myHoursStart + myMinsStart);
                                    timeVipEdit.setText(String.valueOf(timeDelta));

                                }
                                else{
                                    Button buttonDanger = findViewById(R.id.errorButton);
                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Введите корректно время в 2 графах(часы меньше 12 с 0)")

                                            .show();

                                }

                                }
                            });


                    // When user click the save user data button in the popup dialog.
                    saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Get user data from popup dialog editeext.
                            String articul = articulEdit.getText().toString();
                            String kolvo = kolvoEdit.getText().toString();
                            String timeStart = timeStartEdit.getText().toString();
                            String timeEnd = timeEndEdit.getText().toString();
                            String timeVip = timeVipEdit.getText().toString();

                            String passBrigada = passBrigadaEdit.getText().toString();



                            // проверяем наличие пароля в принципе
                            int sch = 0;
                            while (sch<fio_full.passesBrigada.size()){
                                if (passBrigada.equals(fio_full.passesBrigada.get(sch))){
                                    fio_full.passesBrigadaFind = true;


                                }
                                sch = sch + 1;


                            }
                            if (fio_full.passesBrigadaFind){
                                fio_full.passesBrigadaFind = false;
                                if (articul.length()>1 && kolvo.length()>0 && timeStart.length()>1 && timeEnd.length()>1 && timeVip.length()>1 && passBrigada.length()>1  ){
                                    // грузим на web, он перекачает в мес

                                    Date currentDate = new Date();  // Текущая дата
                                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy|HH:mm:ss"); // Задаем формат даты
                                    String formattedDate = sdf.format(currentDate); // и форматируем




                                    String need_otm = "0;"+articul+";"+timeVip+";"+timeStart+";"+timeEnd+";"+kolvo+";"+fio_full.Tabel+";"+fio_full.brigada+";"+formattedDate+";"+passBrigada;
                                    fio_full.sdelano.add(need_otm);


                                    String myURL = "http://10.10.12.165/otmetki/";
                                    String params = need_otm;
                                    byte[] data = null;
                                    InputStream is = null;

                                    try {
                                        URL url = new URL(myURL);
                                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                        conn.setRequestMethod("POST");
                                        conn.setDoOutput(true);
                                        conn.setDoInput(true);


                                        OutputStream os = conn.getOutputStream();
                                        data = params.getBytes(StandardCharsets.ISO_8859_1);
                                        os.write(data);


                                        conn.connect();
                                        int responseCode= conn.getResponseCode();
                                        Log.d(LOG_TAG, "ответ сервера"+responseCode);
                                        Log.d(LOG_TAG, "записываю строку"+params);

                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        is = conn.getInputStream();

                                        byte[] buffer = new byte[8192]; // Такого вот размера буфер
                                        // Далее, например, вот так читаем ответ
                                        int bytesRead;
                                        while ((bytesRead = is.read(buffer)) != -1) {
                                            baos.write(buffer, 0, bytesRead);
                                        }
                                        data = baos.toByteArray();
                                    } catch (Exception e) {
                                        Log.d(LOG_TAG, "упал в ошибку"+params);
                                    } finally {
                                        try {
                                            if (is != null)
                                                is.close();
                                        } catch (Exception ex) {}
                                    }


                                    alertDialog.cancel();
                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)

                                            .setTitleText("Вы уверены")

                                            .setContentText("Информация будет принята в учетных системах")
                                            .setConfirmText("Да, я уверен")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog
                                                            .setTitleText("Отмечено")
                                                            .setContentText("Ваша отметка получена")
                                                            .setConfirmText("OK")
                                                            .setConfirmClickListener(null)
                                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                }
                                            })
                                            .show();

                                }
                                else{
                                    Button buttonDanger = findViewById(R.id.errorButton);
                                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Некорректно заполнены поля")

                                            .show();

                                }
                            }
                            else{
                                // not found Tabel
                                Button buttonDanger = findViewById(R.id.errorButton);
                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Мы не нашли пароль бригады")

                                        .show();


                            }





                        }
                    });
                    cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.cancel();
                        }
                    });



        }
        else{
            // not found Tabel
            Button buttonDanger = findViewById(R.id.errorButton);
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Введите ваш табельный")

                            .show();


        }


    }
    public void Poisk(View view) {

        if (poisk){
            fio_full.poisk_massiv = new ArrayList<String>();
            poisk = false;





            Intent intent = new Intent(view.getContext(), MainActivity.class);

            startActivity(intent);

        }



        else{
            AutoCompleteTextView mAutoCompleteTextView;

            mAutoCompleteTextView = view.findViewById(R.id.autoCompleteTextView2);
            Button test = (Button)findViewById(R.id.button3);
            test.setText("Вернуться к плану");


            Log.d(LOG_TAG, "ищу по строке :  "+ fio_full.poisk_zapros);

            poisk = true;
            int sch = 0;
            while (sch<changed_massiv.size()){
                if (changed_massiv.get(sch).contains(fio_full.poisk_zapros)){
                    poisk_massiv.add(changed_massiv.get(sch));
                    Log.d(LOG_TAG, "добавляю в поиск :  "+ changed_massiv.get(sch));

                }
                sch = sch + 1;
            }

            Intent intent = new Intent(view.getContext(), MainActivity.class);

            startActivity(intent);
        }




    }

    private void initMainActivityControls()
    {
        if(openInputPopupDialogButton == null)
        {
            openInputPopupDialogButton = (Button)findViewById(R.id.button_popup_overlay_input);
        }
        if(userDataListView == null)
        {
            userDataListView = (ListView)findViewById(R.id.listview_user_data);
        }
    }
    /* Initialize popup dialog view and ui controls in the popup dialog. */
    private void initPopupViewControls()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        // Inflate the popup dialog from a layout xml file.
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);
        // Get user input edittext and button ui controls in the popup dialog.
        articulEdit = (EditText) popupInputDialogView.findViewById(R.id.articul);
        kolvoEdit = (EditText) popupInputDialogView.findViewById(R.id.kolvoVne);
        passBrigadaEdit = (EditText) popupInputDialogView.findViewById(R.id.passBrigada);
        timeStartEdit = (MaskedEditText) popupInputDialogView.findViewById(R.id.timeStart);
        timeEndEdit = (MaskedEditText) popupInputDialogView.findViewById(R.id.timeEnd);
        timeVipEdit = (EditText) popupInputDialogView.findViewById(R.id.timeVip);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_save_user_data);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);
        raschetVipBut = (Button) popupInputDialogView.findViewById(R.id.raschetVip);
        // Display values from the main activity list view in user input edittext.
    }


}