package com.example.demo0505;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;

import java.net.CacheRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class E_PunchFragment extends Fragment {

    View view;
    TextView tv_now;
    Button btn_punch;
    EditText et_id;
    RecyclerView recyclerView;
    Adapter adapter;
    Calendar cal;
    SQLiteDatabase db;

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

        /*改成跳動時間*/
        long sysTime = System.currentTimeMillis();
        CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
        tv_now.setText(sysTimeStr);
        new Thread().start();

        /*加入資料庫*/
        SqlDataBaseHelper sqlDataBaseHelper = new SqlDataBaseHelper(getActivity(), "Punch", null, 1);
        db = sqlDataBaseHelper.getReadableDatabase();

        btn_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_punch.getText().equals("上班打卡")) {
                    btn_punch.setText("下班打卡");
                    InsertDB(Integer.parseInt(et_id.getText().toString()));
//                    deleteDb();
                    queryAll();
                } else if (btn_punch.getText().equals("下班打卡")) {
                    btn_punch.setText("上班打卡");
                    Calendar cal = Calendar.getInstance();
                    CharSequence off_time = DateFormat.format("yyyy-MM-dd kk:mm:ss", cal.getTime());
                    data = new HashMap<>();
                    data.put("work", "下班");
                    data.put("time", String.valueOf(off_time));
                    arrayList.add(data);
                }
                adapter.notifyDataSetChanged();
                finish();
            }
        });
    }
    private void initViews(){
        tv_now = view.findViewById(R.id.tv_now);
        btn_punch = view.findViewById(R.id.btn_punch);
        et_id = view.findViewById(R.id.et_id);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }
    private void InsertDB(int enter_id){
        /*取得日期*/
        Calendar cal = Calendar.getInstance();
        CharSequence on_time = DateFormat.format("yyyy-MM-dd kk:mm:ss", cal.getTime());
        /*放到DB*/
        ContentValues cv = new ContentValues();
        cv.put("startTime", on_time.toString());
        cv.put("ID", enter_id);
        db.insert("Punch", null, cv);
    }
    private void queryAll(){
        arrayList.clear();
        Cursor c = db.query("Punch", null, null, null, null, null, null);
        while (c.moveToNext()) {
            data = new HashMap<>();
            data.put("id", c.getString(2));
            data.put("work", "上班");
            data.put("startTime", c.getString(1));
            arrayList.add(data);
            Log.e("id", c.getString(2) + "a");
        }

        c.close();
        Log.e("arrayList", arrayList.toString() + "a");
        Log.e("number", arrayList.size() + "a");

    }
    private void deleteDb(){
        db.delete("Punch", null, null);
    }
    public void finish(){
        if (db.isOpen()) {
            db.close();
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
            TextView tv_id, tv_time, tv_work;
            public ViewHolder(View itemsView){
                super(itemsView);
                tv_id = itemsView.findViewById(R.id.tv_id);
                tv_work = itemsView.findViewById(R.id.tv_work);
                tv_time = itemsView.findViewById(R.id.tv_time);
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
            HashMap<String, String> data = arrayList.get(position);
            holder.tv_id.setText(data.get("id"));
            holder.tv_work.setText(data.get("work"));
            holder.tv_time.setText(data.get("startTime"));
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