package com.example.demo0505;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText ed_User, ed_Pwd;
    TextView tv_error;
    Button btn_login;
    Boolean boss, employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        btn_login.setOnClickListener(view -> {
            /*
            *驗證方式：以密碼來判斷
            * 雇主為A開頭 員工Ｂ開頭（大寫）
            * 用isPasswordValid Method來判斷
            */
//            if(!isPasswordValidBoss(ed_Pwd.getText().toString())){
////                tv_error.setText("密碼錯誤");
//                new AlertDialog.Builder(this)
//                        .setTitle("Alert")
//                        .setMessage("帳號或密碼錯誤")
//                        .setPositiveButton("再試一次",
//                                new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                    }
//                                })
//                        .show();
//            } else if (isPasswordValidBoss(ed_Pwd.getText().toString())) {
//                Intent intent = new Intent(this, BossActivity.class);
//                startActivity(intent);
//            } else if (isPasswordValidEmployee(ed_Pwd.getText().toString())) {
//                Intent intent = new Intent(this, EmployeeActivity.class);
//                startActivity(intent);
//            }
            if (isPasswordValidBoss(ed_Pwd.getText().toString())) {
                Intent intent = new Intent(this, BossActivity.class);
                startActivity(intent);
            } else if (isPasswordValidEmployee(ed_Pwd.getText().toString())) {
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
}