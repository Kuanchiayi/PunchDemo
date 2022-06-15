package com.example.demo0505;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class E_SalaryFragment extends Fragment {

    View view;
    SQLiteDatabase db;
    TextView tv_day, tv_salary;

    ArrayList<HashMap<String,String>> arrayList_all = new ArrayList<>();
    ArrayList<HashMap<String,String>> arrayList_show = new ArrayList<>();
    int[] sum = new int[]{};
    HashMap<String, String> data;
    HashMap<String, String> check;
    String Date, time_former, time_latter;
    Integer time_diff;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_e__salary, container, false);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBaseHelper helper = new DBaseHelper(getActivity(), "PunchCard", null, 2);
        db = helper.getReadableDatabase();
        query_on_All();
        for (int k=0; k<arrayList_all.size(); k+=2) {
            /*0,2,4...*/
            time_former = arrayList_all.get(k).get("Time").substring(0,2);
            /*1,3,5...*/
            time_latter = arrayList_all.get(k+1).get("Time").substring(0,2);
            /*calculate*/
            time_diff = Integer.parseInt(time_latter) - Integer.parseInt(time_former);
            Log.e("timediff", time_diff + "a");
            sum[k] = time_diff;
//            check = new HashMap<>();
//            check.put("Time_diff", String.valueOf(time_diff));
//            check.put("Date", arrayList_all.get(k).get("Date"));
//            arrayList_show.add(check);
        }
        /*加總*/
        Log.e("arrayList_show", arrayList_show + "a");
        Log.e("sumList", sum.toString() + "a");

//        tv_day.setText(String.valueOf(time_diff));
        int result = 0;
        for (int i : sum) {
            result += i;
        }
        tv_day.setText(result);
        tv_salary.setText("2016");
    }

    private void initViews(){
        tv_day = view.findViewById(R.id.tv_day);
        tv_salary = view.findViewById(R.id.tv_salary);
    }

    private void query_on_All(){
        arrayList_all.clear();
        Cursor c = db.query("Punch", null, null, null, null, null, null);
        while (c.moveToNext()) {
            data = new HashMap<>();
            data.put("Date", c.getString(1));
            data.put("work", c.getString(3));
            data.put("Time", c.getString(2));
            arrayList_all.add(data);
        }
        c.close();
        Log.e("arrayList", arrayList_all.toString() + "a");
        Log.e("size", String.valueOf(arrayList_all.size()));
    }
}