package com.example.demo0505;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class E_PunchFragment extends Fragment {

    View view;
    TextView time_now;
    Button btn_punch;
    RecyclerView recyclerView_punch;
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

        Calendar cal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy-MM-dd kk:mm:ss", cal.getTime());
        time_now.setText(s);

        btn_punch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_punch.getText().equals("上班打卡")) {
                    btn_punch.setText("下班打卡");
                } else if (btn_punch.getText().equals("下班打卡")) {
                    btn_punch.setText("上班打卡");
                }
            }
        });
    }
    private void initViews(){
        time_now = view.findViewById(R.id.tv_now);
        btn_punch = view.findViewById(R.id.btn_punch);
        recyclerView_punch = view.findViewById(R.id.recyclerView_punch);
    }
    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

        public ArrayList<HashMap<String,String>> arrayList;

        class ViewHolder extends RecyclerView.ViewHolder{
            public ViewHolder(View itemsView){
                super(itemsView);
                tv_id = itemsView.findViewById(R.id.tv_id);
            }
        }
        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_punch_item, parent, false);
            return new Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
            HashMap<String,String> data = arrayList.get(position);
            holder.tv_forumName.setText(data.get("forumName"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}