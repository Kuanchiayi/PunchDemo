package com.example.demo0505;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class B_BulletinFragment extends Fragment {
    View view;
    FloatingActionButton fab;
    SQLiteDatabase db;
    Adapter adapter;
    RecyclerView recyclerView;
    Button btn_add;

    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String, String> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bulletin, container, false);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onCreateDialog();
            }
        });

        btn_add.setOnClickListener(view1 -> {
            onCreateDialog();
        });
    }

    private void initViews(){
        fab = view.findViewById(R.id.floating_action_button);
        btn_add = view.findViewById(R.id.btn_add);

        recyclerView = view.findViewById(R.id.rcV_boss_bulletin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new B_BulletinFragment.Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void onCreateDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.add_bulletin, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // 使用setView()方法將佈局顯示到dialog
        alertDialog.setView(view);
        EditText et_people = (EditText) view.findViewById(R.id.et_people);
        EditText et_content = (EditText) view.findViewById(R.id.et_content);

        alertDialog
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                InsertDB(String.valueOf(et_people.getText()), String.valueOf(et_content.getText()));
                                query_on_All();
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

    private void InsertDB(String person, String content){
        Cursor c_id = db.query("Bulletin",null, null,null , null, null, null);
        while (c_id.moveToNext()) {
            Log.e("id", c_id.getString(0)+"a");
        }

        ContentValues cv = new ContentValues();
        /*  放入發布者  */
        cv.put("createTime",person);
        /*   放入內文  */
        cv.put("content", content);
        db.insert("Bulletin", null, cv);
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
        Log.e("bulletin", arrayList.toString() + "a" + String.valueOf(arrayList.size()));

        /*
            更新adapter
        * */
        adapter.notifyDataSetChanged();
    }

    public class Adapter extends RecyclerView.Adapter<B_BulletinFragment.Adapter.ViewHolder>{

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
        public B_BulletinFragment.Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_bulletin, parent, false);
            return new B_BulletinFragment.Adapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull B_BulletinFragment.Adapter.ViewHolder holder, int position) {
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