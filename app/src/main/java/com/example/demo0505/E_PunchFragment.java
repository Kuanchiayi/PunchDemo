package com.example.demo0505;

import android.content.Context;
import android.content.DialogInterface;
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
    TextView time_now;
    Button btn_punch;
    RecyclerView recyclerView;
    Adapter adapter;
    Calendar cal;

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
        /*加入資料庫*/
        cal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy-MM-dd kk:mm:ss", cal.getTime());
        time_now.setText(s);


        btn_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_punch.getText().equals("上班打卡")) {
                    btn_punch.setText("下班打卡");
                    /*跳出dialog詢問是誰*/
                    onCreateDialog();
                    /*加入上班*/
                    Calendar cal = Calendar.getInstance();
                    CharSequence on_time = DateFormat.format("yyyy-MM-dd kk:mm:ss", cal.getTime());
                    data = new HashMap<>();
                    data.put("work", "上班");
                    data.put("time", String.valueOf(on_time));
                    arrayList.add(data);
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
            }
        });
    }
    private void initViews(){
        time_now = view.findViewById(R.id.tv_now);
        btn_punch = view.findViewById(R.id.btn_punch);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void onCreateDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.ask_id, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // 使用setView()方法將佈局顯示到dialog
        alertDialog.setView(view);
        EditText userInput = (EditText) view.findViewById(R.id.et_input);

        alertDialog
                .setTitle("輸入貼文數量：")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                data.put("id", String.valueOf(userInput.getText()));
                                arrayList.add(data);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
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
            HashMap<String,String> data = arrayList.get(position);
            holder.tv_id.setText(data.get("id"));
            holder.tv_work.setText(data.get("work"));
            holder.tv_time.setText(data.get("time"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}