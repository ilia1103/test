package com.example.tonar_robots;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tonar_robots.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

import static com.example.tonar_robots.fio_full.changed_massiv;
import static com.example.tonar_robots.fio_full.poisk;
import static com.example.tonar_robots.fio_full.poisk_massiv;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "добавляю в поиск" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);



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
}