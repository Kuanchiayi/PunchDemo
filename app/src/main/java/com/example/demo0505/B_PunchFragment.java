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
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class B_PunchFragment extends Fragment {
    View view;
    TextView tv_now;
    Calendar cal;
    RecyclerView recyclerView;
    B_PunchFragment.Adapter adapter;
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
        view = inflater.inflate(R.layout.fragment_punch, container, false);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*加入資料庫*/
        DBaseHelper helper = new DBaseHelper(getActivity(), "PunchCard", null, 2);
        db = helper.getReadableDatabase();
        /*  進畫面時顯示之前打卡記錄  */
        query_on_All();
    }

    private void initViews(){
        tv_now = view.findViewById(R.id.tv_now);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView = view.findViewById(R.id.recyclerView_punch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new B_PunchFragment.Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
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

    public class Adapter extends RecyclerView.Adapter<B_PunchFragment.Adapter.ViewHolder>{

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
        public B_PunchFragment.Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_punch_item, parent, false);
            return new B_PunchFragment.Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull B_PunchFragment.Adapter.ViewHolder holder, int position) {
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
}