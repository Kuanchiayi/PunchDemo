package com.example.demo0505;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ImageView iv_employee, iv_employer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListener();

        FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDB.getReference("");

    }

    public void initViews(){
        iv_employee = findViewById(R.id.employee);
        iv_employer = findViewById(R.id.employer);
    }

    public void setListener() {
        iv_employer.setOnClickListener(nextPage);
        iv_employee.setOnClickListener(nextPage);
    }

    public View.OnClickListener nextPage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.employee || view.getId()==R.id.employer){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    };
}