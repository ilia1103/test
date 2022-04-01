package com.example.tonar_robots;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.tonar_robots.fio_full.poisk;

public class plan_frag extends Fragment {
    private static final String LOG_TAG = "Строки";
    String house ="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Context cont = getContext();

        View view = inflater.inflate(R.layout.zadanie_frag,
                container, false);



        super.onCreate(savedInstanceState);






        fio_full.thread_run = true;





        RecyclerView recyclerView;
        TextView text = (TextView)view.findViewById(R.id.textView);
        text.setText(fio_full.brigada_name);

        adapter_plan myAdapter = new adapter_plan(cont);

        //recyclerView = findViewById(R.id.vazhno);
        //GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView = view.findViewById(R.id.recycler_plan);

        Button svoboda = (Button)view.findViewById(R.id.svoboda);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(cont, 1) {
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

        ArrayList<String> podr = new ArrayList<String>();

        int sch = 0;

        while (sch<fio_full.changed_massiv.size()){




            String[] stroka =  fio_full.changed_massiv.get(sch).split(";");
            if (!podr.contains(stroka[1])){
                podr.add(stroka[1]);


            }
            sch = sch + 1;




        }
        AutoCompleteTextView mAutoCompleteTextView;

        mAutoCompleteTextView = view.findViewById(R.id.autoCompleteTextView2);

        mAutoCompleteTextView.setAdapter(new ArrayAdapter<>(cont,
                android.R.layout.simple_list_item_1,  podr));





        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem=mAutoCompleteTextView.getAdapter().getItem(position).toString();

                house = selectedItem;
                fio_full.poisk_zapros = house;
                Log.d(LOG_TAG, "выбрал строку :  "+ house);
            }
        });
        if (poisk){
            Button test = (Button)view.findViewById(R.id.button3);
            test.setText("Обратно к плану");
            mAutoCompleteTextView.setVisibility(View.INVISIBLE);
        }
        if (!poisk){
            Button test = (Button)view.findViewById(R.id.button3);
            test.setText("Поиск");
            mAutoCompleteTextView.setVisibility(View.VISIBLE);
        }







        return view;


        // String FILENAME = Environment.getExternalStorageDirectory() + "/DCIM/"+"PominutnyGrafik.txt";

    }
    public class spinner_moi_house implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            house = parent.getItemAtPosition(pos).toString();
            Log.d(LOG_TAG, "выбрал строку :  "+ house);
            fio_full.poisk_zapros = house;
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }



    public void OnGod(View view) {

        Intent intent = new Intent(view.getContext(), God_mode.class);

        startActivity(intent);


    }






    }

