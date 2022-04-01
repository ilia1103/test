package com.example.tonar_robots;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicInteger;

public class plan_fact_frag extends Fragment {

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String LOG_TAG = plan_fact_frag.class.getSimpleName();
        RecyclerView recyclerView_cart;
        Context cont = getContext();

        View view = inflater.inflate(R.layout.plan_fact,
                container, false);

        super.onCreate(savedInstanceState);

        TextView text_fact = view.findViewById(R.id.input_fact_vipoln);
        text_fact.setText(String.valueOf(fio_full.sdelano.size())+" - Отмечено");

        int sch = 0;
        String myTabel = fio_full.Tabel;
        int minutes = 0;

        while (sch<fio_full.sdelano.size()){


            String[] parts_file = fio_full.sdelano.get(sch).split(";");
            if (myTabel.equals(parts_file[5])){
                AtomicInteger need_seconds_nach = new AtomicInteger();

                String[] sec1 =  parts_file[3].split(":");
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
                String[] sec2 =  parts_file[4].split(":");
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
                fio_full.promejutok_need = fio_full.promejutok_need/60000;


                Log.d(LOG_TAG, "секунды конец :  "+  fio_full.promejutok_need);
                minutes = minutes + fio_full.promejutok_need;




            }


            sch = sch + 1;
        }
        TextView kolvoMinut = (TextView)view.findViewById(R.id.kpi_znach);

        kolvoMinut.setText(String.valueOf(minutes));






        return view;

    }

}
