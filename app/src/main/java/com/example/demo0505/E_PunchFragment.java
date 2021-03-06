package com.example.demo0505;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class E_PunchFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

    View view;
    TextView tv_now;
    Button btn_punch, btn_delete;
    RecyclerView recyclerView;
    Adapter adapter;
    SQLiteDatabase db;
    Switch aSwitch;
    FloatingActionButton fab;
    Thread thread;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String, String> data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_e__punch, container, false);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*????????????*/
        long sysTime = System.currentTimeMillis();
        CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
        tv_now.setText(sysTimeStr);
        new Thread().start();

        /*???????????????*/
        DBaseHelper helper = new DBaseHelper(getActivity(), "PunchCard", null, 2);
        db = helper.getReadableDatabase();
        /* switch??????  */
        aSwitch.setOnCheckedChangeListener(this);

        /*  ????????????????????????????????????  */
        query_on_All();

        btn_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("????????????")
                            .setPositiveButton("??????",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            final Calendar cal = Calendar.getInstance();
                                            final CharSequence on_time_date = DateFormat.format("yyyy-MM-dd", cal.getTime());
                                            final CharSequence time = DateFormat.format("kk:mm:ss", cal.getTime());
                                            InsertDB_onDuty(on_time_date.toString(), time.toString());
                                            query_on_All();
                                        }
                                    })
                            .setNegativeButton("cancel", (dialogInterface, i) -> {

                            })
                            .show();
            }
        });

        btn_delete.setOnClickListener(view1 -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("????????????")
                    .setPositiveButton("??????",
                            (dialog, which) -> {
                                deleteDb();
                                arrayList.clear();
                                adapter.notifyDataSetChanged();
                            })
                    .setNegativeButton("cancel", (dialogInterface, i) -> {

                    })
                    .show();
        });


       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               deleteDb();
               arrayList.clear();
               adapter.notifyDataSetChanged();
           }
       });
    }
    private void initViews(){
        tv_now = view.findViewById(R.id.tv_now);
        btn_punch = view.findViewById(R.id.btn_punch);
        aSwitch = view.findViewById(R.id.switch1);
        btn_delete = view.findViewById(R.id.btn_delete);
        fab = view.findViewById(R.id.delete_action_button);

        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }
    private void InsertDB_onDuty(String on_time_date, String on_time_time){
        /*?????????????????????*/
        String workStatus = String.valueOf(btn_punch.getText().subSequence(0,2));
        Cursor c_id = db.query("Employee",null, "ID",null , null, null, null);
        while (c_id.moveToNext()) {
            Log.e("id", c_id.getString(0)+"a");
        }

        ContentValues cv = new ContentValues();
        cv.put("Date", on_time_date);
        cv.put("work", workStatus);
        cv.put("Time", on_time_time);
        cv.put("ID", 1);
        db.insert("Punch", null, cv);
    }
    private void query_on_All(){
        arrayList.clear();

        Cursor c = db.query("Punch", null, null, null, null, null, null);
        while (c.moveToNext()) {
            data = new HashMap<>();
            data.put("Date", c.getString(1));
            data.put("work", c.getString(3));
            data.put("Time", c.getString(2));
            data.put("ID", String.valueOf(c.getInt(4)));
            arrayList.add(data);
        }
        c.close();
        Log.e("arrayList", arrayList.toString() + "a");
        Log.e("size", String.valueOf(arrayList.size()));
        adapter.notifyDataSetChanged();

    }
    private void deleteDb(){
        db.delete("Punch", null, null);
    }
    public void finish(){
        if (db.isOpen()) {
            db.close();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton.isChecked()) {
            btn_punch.setText("????????????");
        } else {
            btn_punch.setText("????????????");
        }
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

        private Context context;
        public ArrayList<HashMap<String,String>> arrayList;
        public Adapter(Context context, ArrayList<HashMap<String,String>> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv_id, tv_date, tv_time, tv_work;
            public ViewHolder(View itemsView){
                super(itemsView);
                tv_id = itemsView.findViewById(R.id.tv_id);
                tv_date = itemsView.findViewById(R.id.tv_date);
                tv_time = itemsView.findViewById(R.id.tv_time);
                tv_work = itemsView.findViewById(R.id.tv_work);
            }
        }

        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_punch_item, parent, false);
            return new Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            HashMap<String,String> data = arrayList.get(position);
            holder.tv_id.setText(data.get("ID"));
            holder.tv_date.setText(data.get("Date"));
            holder.tv_time.setText(data.get("Time"));
            holder.tv_work.setText(data.get("work"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    class Thread extends java.lang.Thread{
        public void run(){
            do {
                try {
                    //?????????????????????
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;

                    // here you check the value of getActivity() and break up if needed
                    if(getActivity() == null)
                        return;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (msg.what) {
                                case 1:
                                    long sysTime = System.currentTimeMillis();
                                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                                    tv_now.setText(sysTimeStr);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}