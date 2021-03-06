package com.example.demo0505;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

public class BossActivity extends AppCompatActivity {

    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);

        initView();
        B_PunchFragment b_punchFragment = new B_PunchFragment();
        openFragment(b_punchFragment);

        /*      ButtonNavigationBar     */
        navigationBarView.getMenu().setGroupCheckable(0, false, false);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                B_PunchFragment BPunchFragment = new B_PunchFragment();
                B_BulletinFragment bulletinFragment = new B_BulletinFragment();
                B_SalaryFragment BSalaryFragment = new B_SalaryFragment();
                switch (item.getItemId()) {
                    case R.id.punch:
                        setTitle("打卡");
                        openFragment(BPunchFragment);
                        break;
                    case R.id.Bulletin:
                        setTitle("公告");
                        openFragment(bulletinFragment);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void initView(){
        navigationBarView = findViewById(R.id.navigation);
    }

    private void openFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_main, fragment);
        fragmentTransaction.commit();
    }
}