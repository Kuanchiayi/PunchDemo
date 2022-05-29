package com.example.demo0505;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class E_SalaryFragment extends Fragment {

    View view;
    SQLiteDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_e__salary, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DBaseHelper helper = new DBaseHelper(getActivity(), "PunchCard", null, 2);
        db = helper.getReadableDatabase();


    }

    public class Adapter extends RecyclerView.Adapter<E_SalaryFragment.Adapter.ViewHolder>{

        private Context context;
        public ArrayList<HashMap<String,String>> arrayList;
        public Adapter(Context context, ArrayList<HashMap<String,String>> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv_id, tv_date, tv_time_on, tv_time_off;
            public ViewHolder(View itemsView){
                super(itemsView);
                tv_id = itemsView.findViewById(R.id.tv_id);
                tv_date = itemsView.findViewById(R.id.tv_date);
                tv_time_on = itemsView.findViewById(R.id.tv_time);
                tv_time_off = itemsView.findViewById(R.id.tv_time_off);
            }
        }

        @NonNull
        @Override
        public E_SalaryFragment.Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_salary_items, parent, false);
            return new E_SalaryFragment.Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull E_SalaryFragment.Adapter.ViewHolder holder, int position) {
            HashMap<String,String> data = arrayList.get(position);
//            holder.tv_id.setText(data.get("ID"));
            holder.tv_date.setText(data.get("Date"));
            holder.tv_time_on.setText(data.get("onTime"));
            holder.tv_time_off.setText(data.get("offTime"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}