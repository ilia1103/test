package com.example.tonar_robots;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class adapter_plan extends RecyclerView.Adapter<adapter_plan.MyViewSub> {
    private boolean isImageScaled = false;
    Context context;
    int promejutok = 0;


    public adapter_plan(Context ct){

        context = ct;

    }
    @NonNull
    @Override
    public MyViewSub onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_plan,parent,false);
        return new MyViewSub(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewSub holder, @SuppressLint("RecyclerView") int position) {

        final String[] iz_otpuska = new String[1];
        String LOG_TAG = Login.class.getSimpleName();

        holder.vipoln.setVisibility(View.VISIBLE);
        if (position == 0) {


            //holder.itemView.setBackgroundColor(Color.GREEN);
        }
        else {
            //holder.itemView.setBackgroundColor(Color.RED);
        }
        String[] parts_file = fio_full.changed_massiv.get(position).split(";");

        if (fio_full.poisk){
             parts_file = fio_full.poisk_massiv.get(position).split(";");


        }
        holder.fio.setText(parts_file[1].replace("_","\n"));

        holder.vozrast.setText(parts_file[3]);
        holder.dolzhnost.setText(parts_file[4]);
        AtomicInteger need_seconds_nach = new AtomicInteger();


        String[] finalParts_file = parts_file;



        String[] sec1 =  parts_file[3].split(":");
        String hours1 = sec1[0];
        String minurtes1 = sec1[1];



        Integer kolvoFromGraph = Integer.parseInt(parts_file[5]);
        holder.kolvoText.setText(String.valueOf(kolvoFromGraph));


        if (hours1.charAt(0) == '0'){
            hours1 = hours1.substring(1,2);

        }
        if (minurtes1.charAt(0) == '0'){
            minurtes1 = minurtes1.substring(1,2);

        }
        need_seconds_nach.set((Integer.parseInt(hours1) * 60 * 60 + Integer.parseInt(minurtes1) * 60) * 1000);
        Log.d(LOG_TAG, "секунды нач :  "+  need_seconds_nach);

        int need_seconds_end = 0;
        String[] sec2 =  finalParts_file[4].split(":");
        String hours2 = sec2[0];
        String minurtes2 = sec2[1];

        if (hours2.charAt(0) == '0'){
            hours2 = hours2.substring(1,2);

        }
        if (minurtes2.charAt(0) == '0'){
            minurtes2 = minurtes2.substring(1,2);

        }
        need_seconds_end = (Integer.parseInt(hours2)*60*60+Integer.parseInt(minurtes2)*60)*1000;
        Log.d(LOG_TAG, "секунды конец :  "+  need_seconds_end);

        int stoimostMin = (need_seconds_end - need_seconds_nach.get())/60000;
        // Здесь получили минуты из текстовика и пересчитали по коэф



        Integer kolvoMin = Integer.parseInt(parts_file[2]);
        double stoimost = kolvoMin*8.2;
        int final_cena = (int)Math.round(stoimost);
        int cenaForOne = final_cena/kolvoFromGraph;

        holder.stoimost1.setText(String.valueOf(cenaForOne));

        holder.stoimost.setText(String.valueOf(final_cena)+" ₽");



        holder.draw.setOnClickListener(v -> {


            String wat = String.valueOf(holder.fio.getText()).toUpperCase();
            wat = wat.replace(" ","").replace("_","");



            ///ЭТИ 2 СТРОЧКИ ВАЖНЫ
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);


            BufferedInputStream in = null;
            FileOutputStream fout = null;


            try{
                saveUrl(Environment.getExternalStorageDirectory() + "/DCIM/"+wat+".SLDDRW", "http://10.11.1.238/slddrw/"+wat+".SLDDRW",wat);
                Toast.makeText(context, "Подождите,файл загружается...." , Toast.LENGTH_SHORT).show();



            }
            catch(Exception e){
                //Обработайте ошибку
                //Toast.makeText(this, "НЕ СКАЧАЛ", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, String.valueOf(e) , Toast.LENGTH_SHORT).show();
            }


            });


        holder.plus.setOnClickListener(v -> {
            String wtf = String.valueOf(holder.kolvoText.getText());
            int wtfInt = Integer.parseInt(wtf);
            if (wtfInt<kolvoFromGraph && wtfInt>0){

                wtfInt = wtfInt + 1;
                fio_full.kolvoVip = wtfInt;
                Log.d(LOG_TAG, "сейчас количество :  "+fio_full.kolvoVip);
                holder.kolvoText.setText(String.valueOf(wtfInt));

                String[] cenaAll = String.valueOf(holder.stoimost.getText()).split(" ");
                int cena = Integer.parseInt(cenaAll[0]);
                int cenaNeed = cena + Integer.parseInt(String.valueOf(holder.stoimost1.getText()));
                holder.stoimost.setText(String.valueOf(cenaNeed));





            }
            else {
                Toast.makeText(context, "Недопустимое значение количества!", Toast.LENGTH_SHORT).show();
            }


        });


        holder.minus.setOnClickListener(v -> {
            String wtf = String.valueOf(holder.kolvoText.getText());
            int wtfInt = Integer.parseInt(wtf);
            if (wtfInt<kolvoFromGraph+1 && wtfInt>1){

                wtfInt = wtfInt - 1;
                fio_full.kolvoVip = wtfInt;
                Log.d(LOG_TAG, "сейчас количество :  "+fio_full.kolvoVip);
                holder.kolvoText.setText(String.valueOf(wtfInt));

                String[] cenaAll = String.valueOf(holder.stoimost.getText()).split(" ");
                int cena = Integer.parseInt(cenaAll[0]);
                int cenaNeed = cena - Integer.parseInt(String.valueOf(holder.stoimost1.getText()));
                holder.stoimost.setText(String.valueOf(cenaNeed));

            }
            else {

                Toast.makeText(context, "Недопустимое значение количества!", Toast.LENGTH_SHORT).show();

            }


        });




        holder.vipoln.setOnClickListener(v -> {

            if (fio_full.Tabel.length()<2){
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
                builder.setTitle("Введите ваш табельный");

// Set up the input
                final EditText input = new EditText(context);
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
                            dialog.cancel();
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Вы подтверждаете выполнение?")
                                    .setMessage("Информация будет принята во всех учетных системах")
                                    .setPositiveButton("Подтверждаю",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {

                                                    int cifra = position;
                                                    String[] parts_file1 = fio_full.changed_massiv.get(position).split(";");
                                                    String stroka = (String) holder.fio.getText();
                                                    stroka = stroka.replace("\n","_");

                                                    if (fio_full.poisk){
                                                        int sch = 0;
                                                        while (sch<fio_full.changed_massiv.size()){
                                                            //здесь ищем настоящий индекс в массиве, когда это поиск. Нужно теперь будет сравнивать без количества, отрезать последнюю часть

                                                            if (fio_full.changed_massiv.get(sch).equals(fio_full.poisk_massiv.get(position))){
                                                                cifra = sch;
                                                                Log.d(LOG_TAG, "НАШЕЛ СООТВЕСТВТВИВЕ :  "+  cifra);
                                                            }
                                                            sch = sch + 1;
                                                        }

                                                    }


                                                    String[] sec1 =  parts_file1[3].split(":");
                                                    String hours1 = sec1[0];
                                                    String minurtes1 = sec1[1];

                                                    if (hours1.substring(0,1)=="0"){
                                                        hours1 = hours1.substring(1,2);

                                                    }
                                                    if (minurtes1.substring(0,1)=="0"){
                                                        minurtes1 = minurtes1.substring(1,2);

                                                    }
                                                    need_seconds_nach.set((Integer.parseInt(hours1) * 60 * 60 + Integer.parseInt(minurtes1) * 60) * 1000);
                                                    Log.d(LOG_TAG, "секунды нач :  "+  need_seconds_nach);

                                                    int need_seconds_end = 0;
                                                    String[] sec2 =  finalParts_file[4].split(":");
                                                    String hours2 = sec2[0];
                                                    String minurtes2 = sec2[1];

                                                    if (hours2.charAt(0) == '0'){
                                                        hours2 = hours2.substring(1,2);

                                                    }
                                                    if (minurtes2.charAt(0) == '0'){
                                                        minurtes2 = minurtes2.substring(1,2);

                                                    }
                                                    need_seconds_end = (Integer.parseInt(hours2)*60*60+Integer.parseInt(minurtes2)*60)*1000;
                                                    Log.d(LOG_TAG, "секунды конец :  "+  need_seconds_end);

                                                    fio_full.promejutok_need = need_seconds_end - need_seconds_nach.get();



                                                    fio_full.thread_run = false;
                                                    Log.d(LOG_TAG, "размер:  "+ fio_full.changed_massiv.size());
                                                    Log.d(LOG_TAG, "позиция:  "+ position);

                                                    Intent intent = new Intent(context, MainActivity.class);
                                                    String my_str ="";
                                                    if (cifra<fio_full.changed_massiv.size()-1){
                                                        my_str = fio_full.changed_massiv.get(cifra)+";"+fio_full.Tabel + "\n" + fio_full.changed_massiv.get(cifra + 1);
                                                    }
                                                    else{
                                                        my_str = fio_full.changed_massiv.get(cifra)+";"+fio_full.Tabel + "\n" + "Конец плана";

                                                    }


                                                    //здесь пишем про количество, добавляем, если меньше указанного в changed разницу

                                                    Log.d(LOG_TAG, "прошел в неравенство количества :  "+fio_full.kolvoVip+"  - "+finalParts_file[5]);
                                                    if (fio_full.kolvoVip!=Integer.parseInt(finalParts_file[5])&&fio_full.kolvoVip!=0){
                                                        int kolvoVipoln = Integer.parseInt(finalParts_file[5]) - fio_full.kolvoVip ;
                                                        String newStr = finalParts_file[0]+";"+ finalParts_file[1]+";"+ finalParts_file[2]+";"+finalParts_file[3]+";"+finalParts_file[4]+";"+String.valueOf(fio_full.kolvoVip);
                                                        String newStrMassiv = finalParts_file[0]+";"+ finalParts_file[1]+";"+finalParts_file[2]+";"+finalParts_file[3]+";"+finalParts_file[4]+";"+String.valueOf(kolvoVipoln);

                                                        fio_full.sdelano.add(newStr+";"+fio_full.Tabel);
                                                        fio_full.changed_massiv.set(cifra,newStrMassiv) ;
                                                        fio_full.kolvoVip = 0;


                                                    }
                                                    else{
                                                        fio_full.sdelano.add(fio_full.changed_massiv.get(cifra)+";"+fio_full.Tabel);
                                                        //здесь пишем про количество, добавляем, если меньше указанного в changed разницу

                                                        fio_full.changed_massiv.remove(cifra);
                                                    }




                                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                                    StorageReference storageReference = storage.getReference();
                                                    StorageReference ref = storageReference.child("now_work/now_"+fio_full.brigada_name+".txt");
                                                    StorageReference ref_all = storageReference.child("all_now/all_"+fio_full.brigada_name+".txt");
                                                    byte[] b = my_str.getBytes(StandardCharsets.UTF_8);
                                                    ref.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Log.d(LOG_TAG, "записал :  ");
                                                            //Toast.makeText(main_screen.this, "Заявка отправлена, обработка в конце дня", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                                    int sch_for_all_work = 0;
                                                    String all_work = "";
                                                    while (sch_for_all_work<fio_full.sdelano.size()) {

                                                        all_work = all_work + fio_full.sdelano.get(sch_for_all_work)+";"+fio_full.Tabel + "\n";
                                                        sch_for_all_work = sch_for_all_work + 1;

                                                    }


                                                    byte[] all = all_work.getBytes(StandardCharsets.UTF_8);
                                                    ref_all.putBytes(all).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Log.d(LOG_TAG, "записал :  ");
                                                            //Toast.makeText(main_screen.this, "Заявка отправлена, обработка в конце дня", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });










                                                    context.startActivity(intent);


                                                }
                                            })
                                    .setNegativeButton("Отмена",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });


                            AlertDialog dialog1 = builder.create();
                            dialog1.show();

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
            else{

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Вы подтверждаете выполнение?")
                        .setMessage("Информация будет принята во всех учетных системах")
                        .setPositiveButton("Подтверждаю",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        int cifra = position;
                                        String[] parts_file1 = fio_full.changed_massiv.get(position).split(";");
                                        String stroka = (String) holder.fio.getText();
                                        stroka = stroka.replace("\n","_");

                                        if (fio_full.poisk){
                                            int sch = 0;
                                            while (sch<fio_full.changed_massiv.size()){
                                                //здесь ищем настоящий индекс в массиве, когда это поиск. Нужно теперь будет сравнивать без количества, отрезать последнюю часть
                                                if (fio_full.changed_massiv.get(sch).equals(fio_full.poisk_massiv.get(position))){
                                                    cifra = sch;
                                                    Log.d(LOG_TAG, "НАШЕЛ СООТВЕСТВТВИЕ :  "+  cifra);
                                                }
                                                sch = sch + 1;
                                            }

                                        }


                                        String[] sec1 =  parts_file1[3].split(":");
                                        String hours1 = sec1[0];
                                        String minurtes1 = sec1[1];

                                        if (hours1.substring(0,1)=="0"){
                                            hours1 = hours1.substring(1,2);

                                        }
                                        if (minurtes1.substring(0,1)=="0"){
                                            minurtes1 = minurtes1.substring(1,2);

                                        }
                                        need_seconds_nach.set((Integer.parseInt(hours1) * 60 * 60 + Integer.parseInt(minurtes1) * 60) * 1000);
                                        Log.d(LOG_TAG, "секунды нач :  "+  need_seconds_nach);

                                        int need_seconds_end = 0;
                                        String[] sec2 =  finalParts_file[4].split(":");
                                        String hours2 = sec2[0];
                                        String minurtes2 = sec2[1];

                                        if (hours2.substring(0, 1).equals("0")){
                                            hours2 = hours2.substring(1,2);

                                        }
                                        if (minurtes2.substring(0, 1).equals("0")){
                                            minurtes2 = minurtes2.substring(1,2);

                                        }
                                        need_seconds_end = (Integer.parseInt(hours2)*60*60+Integer.parseInt(minurtes2)*60)*1000;
                                        Log.d(LOG_TAG, "секунды конец :  "+  need_seconds_end);

                                        fio_full.promejutok_need = need_seconds_end - need_seconds_nach.get();



                                        fio_full.thread_run = false;
                                        Log.d(LOG_TAG, "размер:  "+ fio_full.changed_massiv.size());
                                        Log.d(LOG_TAG, "позиция:  "+ position);

                                        Intent intent = new Intent(context, MainActivity.class);
                                        String my_str ="";
                                        if (cifra<fio_full.changed_massiv.size()-1){
                                            my_str = fio_full.changed_massiv.get(cifra)+";"+fio_full.Tabel + "\n" + fio_full.changed_massiv.get(cifra + 1);
                                        }
                                        else{
                                            my_str = fio_full.changed_massiv.get(cifra)+";"+fio_full.Tabel + "\n" + "Конец плана";

                                        }

                                        Log.d(LOG_TAG, "прошел в неравенство количества :  "+fio_full.kolvoVip+"  - "+finalParts_file[5]);
                                        if (fio_full.kolvoVip!=Integer.parseInt(finalParts_file[5])&&fio_full.kolvoVip!=0){
                                            int kolvoVipoln = Integer.parseInt(finalParts_file[5]) - fio_full.kolvoVip ;
                                            String newStr = finalParts_file[0]+";"+ finalParts_file[1]+";"+ finalParts_file[2]+";"+finalParts_file[3]+";"+finalParts_file[4]+";"+String.valueOf(fio_full.kolvoVip);
                                            String newStrMassiv = finalParts_file[0]+";"+ finalParts_file[1]+";"+finalParts_file[2]+";"+finalParts_file[3]+";"+finalParts_file[4]+";"+String.valueOf(kolvoVipoln);

                                            fio_full.sdelano.add(newStr+";"+fio_full.Tabel);
                                            fio_full.changed_massiv.set(cifra,newStrMassiv) ;
                                            fio_full.kolvoVip = 0;


                                        }
                                        else{
                                            fio_full.sdelano.add(fio_full.changed_massiv.get(cifra)+";"+fio_full.Tabel);
                                            //здесь пишем про количество, добавляем, если меньше указанного в changed разницу

                                            fio_full.changed_massiv.remove(cifra);
                                        }



                                        FirebaseStorage storage = FirebaseStorage.getInstance();
                                        StorageReference storageReference = storage.getReference();
                                        StorageReference ref = storageReference.child("now_work/now_"+fio_full.brigada_name+".txt");
                                        StorageReference ref_all = storageReference.child("all_now/all_"+fio_full.brigada_name+".txt");
                                        byte[] b = my_str.getBytes(StandardCharsets.UTF_8);
                                        ref.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Log.d(LOG_TAG, "записал :  ");
                                                //Toast.makeText(main_screen.this, "Заявка отправлена, обработка в конце дня", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                        int sch_for_all_work = 0;
                                        String all_work = "";
                                        while (sch_for_all_work<fio_full.sdelano.size()) {

                                            all_work = all_work + fio_full.sdelano.get(sch_for_all_work)+";"+fio_full.Tabel + "\n";
                                            sch_for_all_work = sch_for_all_work + 1;

                                        }


                                        byte[] all = all_work.getBytes(StandardCharsets.UTF_8);
                                        ref_all.putBytes(all).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Log.d(LOG_TAG, "записал :  ");
                                                //Toast.makeText(main_screen.this, "Заявка отправлена, обработка в конце дня", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                        String myURL = "http://1csrv05/Phone/ru/hs/tfj/Call";
                                        String params = "Authorization:Basic X9Ch0L7RhNGC0KE6MDEyMw==";
                                        byte[] data = null;
                                        InputStream is = null;

                                        try {
                                            URL url = new URL(myURL);
                                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                            conn.setRequestMethod("POST");
                                            conn.setDoOutput(true);
                                            conn.setDoInput(true);

                                            conn.setRequestProperty("Content-Length", "" + Integer.toString(params.getBytes().length));
                                            OutputStream os = conn.getOutputStream();
                                            data = params.getBytes("UTF-8");
                                            os.write(data);


                                            conn.connect();
                                            int responseCode= conn.getResponseCode();

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
                                        } finally {
                                            try {
                                                if (is != null)
                                                    is.close();
                                            } catch (Exception ex) {}
                                        }






                                        context.startActivity(intent);


                                    }
                                })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });


                AlertDialog dialog = builder.create();
                dialog.show();

            }






        });









        //аватарки дни рождений

























    };





    @Override
    public int getItemCount() {

        int count = fio_full.changed_massiv.size();
        if (fio_full.poisk){
            count = fio_full.poisk_massiv.size();
        }
        return count;
    }



    public class MyViewSub extends RecyclerView.ViewHolder {

        TextView dolzhnost,fio,vozrast,stoimost,stoimost1;
        Button vipoln;
        ImageButton plus,minus;
        ImageView draw;
        TextInputEditText kolvoText;



        public MyViewSub(@NonNull View itemView) {
            super(itemView);


            plus = itemView.findViewById(R.id.imageButton);
            minus = itemView.findViewById(R.id.imageButton3);
            kolvoText = itemView.findViewById(R.id.kolvo);
            vipoln = itemView.findViewById(R.id.button2);


            draw = itemView.findViewById(R.id.imageView4);
            stoimost = itemView.findViewById(R.id.textView7);
            stoimost1 = itemView.findViewById(R.id.stoimost1);
            dolzhnost = itemView.findViewById(R.id.dolzhnost);
            fio = itemView.findViewById(R.id.fio_birthday);
            vozrast = itemView.findViewById(R.id.vozrast);



        }
    }
    public void saveUrl(final String filename, final String urlString,final String name)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;


        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        }
        catch (IOException e) {
            Toast.makeText(context, "Файл не найден в хранилище!" , Toast.LENGTH_SHORT).show();

        }
        finally {

            if (in != null) {
                in.close();


                String localUri = "/storage/emulated/0/DCIM/"+name+".SLDDRW"; //тут уже как хотите так и формируйте путь, хоть через Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + имя файла

                // url = file path or whatever suitable URL you want.

                File file = new File(localUri);
                Uri contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                Intent openFileIntent = new Intent(Intent.ACTION_VIEW);

                openFileIntent.setDataAndTypeAndNormalize(contentUri,  getMimeType(localUri));
                openFileIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(openFileIntent);



                //String path = "/storage/emulated/0/DCIM/ПТ12-3001043.SLDDRW";
                //File file = new File(path);
                //pdfOpenintent.setDataAndType(photoURI, "application/slddrw");
                //pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);





            }
            if (fout != null) {
                fout.close();

            }
        }
    }
    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        Log.d("moe", "ТИП :  "+  type);

        return type;
    }
}

