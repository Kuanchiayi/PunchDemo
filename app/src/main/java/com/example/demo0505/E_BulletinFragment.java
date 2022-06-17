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

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class E_BulletinFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    Adapter adapter;
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
        view = inflater.inflate(R.layout.fragment_e__bulletin, container, false);
        initView();
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

    private void initView(){
        recyclerView = view.findViewById(R.id.rcV_employee_bulletin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new E_BulletinFragment.Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void query_on_All(){
        arrayList.clear();

        Cursor c = db.query("Bulletin", null, null, null, null, null, null);
        while (c.moveToNext()) {
            data = new HashMap<>();
            final Calendar cal = Calendar.getInstance();
            final CharSequence time = DateFormat.format("yyyy-MM-dd kk:mm:ss", cal.getTime());

            data.put("person", c.getString(1));
            data.put("content", c.getString(2));
            data.put("time", String.valueOf(time));
            arrayList.add(data);
        }
        c.close();
        /*
            更新adapter
        * */
        adapter.notifyDataSetChanged();
    }

    public class Adapter extends RecyclerView.Adapter<E_BulletinFragment.Adapter.ViewHolder>{

        private Context context;
        public ArrayList<HashMap<String,String>> arrayList;
        public Adapter(Context context, ArrayList<HashMap<String,String>> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv_person, tv_time, tv_content;
            public ViewHolder(View itemsView){
                super(itemsView);
                tv_person = itemsView.findViewById(R.id.tv_person);
                tv_content = itemsView.findViewById(R.id.tv_content);
                tv_time = itemsView.findViewById(R.id.tv_time);
            }
        }

        @NonNull
        @Override
        public E_BulletinFragment.Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_bulletin, parent, false);
            return new E_BulletinFragment.Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull E_BulletinFragment.Adapter.ViewHolder holder, int position) {
            HashMap<String,String> data = arrayList.get(position);
            holder.tv_person.setText(data.get("person"));
            holder.tv_content.setText(data.get("content"));
            holder.tv_time.setText(data.get("time"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}