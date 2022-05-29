package com.example.demo0505;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.net.CacheRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class E_PunchFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

    View view;
    TextView tv_now;
    Button btn_punch;
    Calendar cal;
    RecyclerView recyclerView;
    Adapter adapter;
    SQLiteDatabase db;
    Switch aSwitch;

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

        /*顯示時間*/
        long sysTime = System.currentTimeMillis();
        CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
        tv_now.setText(sysTimeStr);
        new Thread().start();

        /*加入資料庫*/
        DBaseHelper helper = new DBaseHelper(getActivity(), "PunchCard", null, 2);
        db = helper.getReadableDatabase();
        /* switch設定  */
        aSwitch.setOnCheckedChangeListener(this);

        /*  進畫面時顯示之前打卡記錄  */
        query_on_All();

        btn_punch.setOnClickListener(new View.OnClickListener() {
            final Calendar cal = Calendar.getInstance();
            final CharSequence on_time_date = DateFormat.format("yyyy-MM-dd", cal.getTime());
            final CharSequence on_time_time = DateFormat.format("kk:mm:ss", cal.getTime());
            @Override
            public void onClick(View view) {
//                deleteDb();
                    new AlertDialog.Builder(getActivity())
                            .setTitle("確認打卡")
                            .setPositiveButton("確定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            InsertDB_onDuty(on_time_date.toString(), on_time_time.toString());
                                            query_on_All();
                                        }
                                    })
                            .setNegativeButton("cancel", (dialogInterface, i) -> {

                            })
                            .show();
            }
        });
    }
    private void initViews(){
        tv_now = view.findViewById(R.id.tv_now);
        btn_punch = view.findViewById(R.id.btn_punch);
        aSwitch = view.findViewById(R.id.switch1);

        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }
    private void InsertDB_onDuty(String on_time_date, String on_time_time){
        /*抓取上下班狀態*/
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
            btn_punch.setText("上班打卡");
        } else {
            btn_punch.setText("下班打卡");
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
                    //延遲兩秒後更新
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;

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