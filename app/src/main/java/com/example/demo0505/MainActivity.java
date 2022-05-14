package com.example.demo0505;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btn_boss, btn_employer;
    ImageView iv_avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListener();
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BossActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initViews(){
        btn_boss = findViewById(R.id.btn_boss);
        btn_employer = findViewById(R.id.btn_employee);
        iv_avatar = findViewById(R.id.iv_avatar);
    }

    public void setListener() {
        btn_employer.setOnClickListener(nextPage);
        btn_boss.setOnClickListener(nextPage);
    }

    public View.OnClickListener nextPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.btn_employee || view.getId() == R.id.btn_boss){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };
}