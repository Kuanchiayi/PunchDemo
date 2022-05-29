package com.example.demo0505;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    EditText ed_User, ed_Pwd;
    TextView tv_error;
    Button btn_login;
    Boolean boss, employee;
    SQLiteDatabase db;

    String id;
    ArrayList<String> id_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        DBaseHelper helper = new DBaseHelper(this, "PunchCard", null, 2);
        db = helper.getReadableDatabase();

        btn_login.setOnClickListener(view -> {
            /*
            *驗證方式：以密碼來判斷
            * 雇主為A開頭 員工Ｂ開頭（大寫）
            * 用isPasswordValid Method來判斷
            */
            if (isPasswordValidBoss(ed_Pwd.getText().toString())) {
                Intent intent = new Intent(this, BossActivity.class);
                startActivity(intent);
            } else if (isPasswordValidEmployee(ed_Pwd.getText().toString())) {
                /*儲存ID&pwd*/
                InsertDB_ID();
                Intent intent = new Intent(this, EmployeeActivity.class);
                startActivity(intent);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Alert")
                        .setMessage("帳號或密碼錯誤")
                        .setPositiveButton("再試一次",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                        .show();
            }
        });
    }

    private void initViews(){
        ed_User = findViewById(R.id.et_user);
        ed_Pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
        tv_error = findViewById(R.id.tv_error);
    }

    /*"isPasswordValid" from "Navigate to the next Fragment" section method goes here */
    private boolean isPasswordValidBoss(@Nullable String text){
        boss = text != null && text.charAt(0) == 'A';
        return boss;
    }

    private boolean isPasswordValidEmployee(String text){
        employee = text != null && text.charAt(0) == 'B';
        return employee;
    }

    private void InsertDB_ID(){
        /* 放id到DB 格式：1 (假設員工為個位數)*/
        /* 放pwd到DB */

        Cursor c = db.query("Employee", null, null, null, null, null, null);
        while (c.moveToNext()) {
            id_list.add(c.getString(0));
        }
        id = String.valueOf(ed_User.getText().subSequence(ed_User.getText().length()-1, ed_User.getText().length()));
        if (id_list.contains(id)) {
            ContentValues cv = new ContentValues();
            cv.put("ID", id);
            cv.put("password", ed_Pwd.getText().toString());
            db.insert("Employee", null, cv);
            Log.e("id_list", id_list+"a");
        }
    }
}