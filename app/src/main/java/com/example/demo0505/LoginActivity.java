package com.example.demo0505;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
            if(!isPasswordValid(ed_Pwd.getText().toString())){
                tv_error.setText("密碼錯誤");
            }else{
                Intent intent = new Intent(this, BossActivity.class);
                startActivity(intent);
            }

            /*     登入後跳至雇主畫面    */
//            ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false);

        });
    }

    private void initViews(){
        ed_User = findViewById(R.id.et_user);
        ed_Pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
        tv_error = findViewById(R.id.tv_error);
    }

    /*"isPasswordValid" from "Navigate to the next Fragment" section method goes here */
    private boolean isPasswordValid(@Nullable String text){
        return text != null && text.charAt(0) == 'A';
    }
}